package me.deftware.mixin.mixins.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.events.EventWeather;
import me.deftware.client.framework.render.camera.entity.CameraEntityMan;
import me.deftware.client.framework.render.shader.ShaderTarget;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer {

	@Shadow
	@Final
	private BufferBuilderStorage bufferBuilders;

	@Shadow
	protected abstract void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers);

	@Shadow
	protected abstract boolean canDrawEntityOutlines();

	@Inject(method = "tickRainSplashing", at = @At("HEAD"), cancellable = true)
	private void renderRain(Camera camera, CallbackInfo ci) {
		EventWeather event = new EventWeather(EventWeather.WeatherType.Rain);
		event.broadcast();
		if (event.isCanceled()) {
			ci.cancel();
		}
	}

	@Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
	private void renderWeather(LightmapTextureManager manager, float tickDelta, double posX, double posY, double posZ, CallbackInfo ci) {
		EventWeather event = new EventWeather(EventWeather.WeatherType.Rain);
		event.broadcast();

		if (event.isCanceled()) {
			ci.cancel();
		}
	}

	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;setupTerrain(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/Frustum;ZIZ)V"), index = 4)
	public boolean isSpectator(boolean spectator) {
		return spectator || CameraEntityMan.isActive();
	}

    /*
        Shader
     */

	@Unique
	private ShaderTarget shaderTarget;

	@Unique
	private boolean canDrawCustomBuffers() {
		if (!FrameworkConstants.OPTIFINE) {
			return true;
		}
		return FrameworkConstants.CAN_RENDER_SHADER;
	}

	@Inject(method = "loadEntityOutlineShader", at = @At("HEAD"))
	public void loadEntityOutlineShader(CallbackInfo ci) {
		if (canDrawCustomBuffers())
			Arrays.stream(ShaderTarget.values()).forEach(target -> target.init(bufferBuilders.getEntityVertexConsumers()));
	}

	@Inject(method = "onResized", at = @At("HEAD"))
	public void onResized(int width, int height, CallbackInfo ci) {
		if (canDrawCustomBuffers())
			Arrays.stream(ShaderTarget.values()).forEach(target -> target.onResized(width, height));
	}

	@Redirect(method = "drawEntityOutlinesFramebuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;canDrawEntityOutlines()Z", opcode = 180))
	public boolean drawEntityOutlinesFramebuffer(WorldRenderer worldRenderer) {
		if (canDrawCustomBuffers()) {
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
			Arrays.stream(ShaderTarget.values()).forEach(ShaderTarget::renderBuffer);
			RenderSystem.disableBlend();
			return false;
		}
		return canDrawEntityOutlines();
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;canDrawEntityOutlines()Z", opcode = 180, ordinal = 0))
	private boolean onRenderHead(WorldRenderer worldRenderer) {
		if (canDrawCustomBuffers()) Arrays.stream(ShaderTarget.values()).forEach(ShaderTarget::clear);
		if (!FrameworkConstants.OPTIFINE) return true;
		return canDrawEntityOutlines();
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/BlockEntityRenderDispatcher;render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", opcode = 180, ordinal = 0))
	private void renderBlocKEntity(BlockEntityRenderDispatcher blockEntityRenderDispatcher, BlockEntity blockEntity, float tickDelta, MatrixStack matrix, VertexConsumerProvider original) {
		boolean flag;
		if (flag = canDrawCustomBuffers() && ShaderTarget.STORAGE.isEnabled() && (blockEntity instanceof LootableContainerBlockEntity || blockEntity instanceof EnderChestBlockEntity)) {
			shaderTarget = ShaderTarget.STORAGE;
		}
		BlockEntityRenderDispatcher.INSTANCE.render(blockEntity, tickDelta, matrix,
				flag ? shaderTarget.getOutlineVertexConsumerProvider() : original
		);
	}

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OutlineVertexConsumerProvider;draw()V", opcode = 180))
	private void onRenderOutlineVertexConsumers(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
		// Draw custom outline vertex, only required for block entities
		if (canDrawCustomBuffers() && shaderTarget != null && shaderTarget == ShaderTarget.STORAGE)
			shaderTarget.getOutlineVertexConsumerProvider().draw();
	}

	@Inject(method = "getEntityOutlinesFramebuffer", at = @At("HEAD"), cancellable = true)
	public void getEntityOutlinesFramebufferInject(CallbackInfoReturnable<Framebuffer> cir) {
		if (canDrawCustomBuffers() && shaderTarget != null && shaderTarget.getFramebuffer() != null) {
			// Return our custom frame buffer
			cir.setReturnValue(shaderTarget.getFramebuffer());
		}
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderEntity(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", opcode = 180))
	private void doRenderEntity(WorldRenderer worldRenderer, Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
		boolean enabled;
		// Player
		if (entity instanceof PlayerEntity) {
			if (enabled = ShaderTarget.PLAYER.isEnabled()) {
				shaderTarget = ShaderTarget.PLAYER;
			}
		} else if (entity instanceof ItemEntity) {
			if (enabled = ShaderTarget.DROPPED.isEnabled()) {
				shaderTarget = ShaderTarget.DROPPED;
			}
		} else if (enabled = ShaderTarget.ENTITY.isEnabled()) {
			shaderTarget = ShaderTarget.ENTITY;
		}
		if (enabled) enabled = canDrawCustomBuffers();
		// Check target
		if (enabled) {
			enabled = shaderTarget.getPredicate().test(
					new ChatMessage().fromText(entity.getType().getName()).toString(false)
			);
		}
		renderEntity(entity, cameraX, cameraY, cameraZ, tickDelta, matrices,
				enabled ? shaderTarget.getOutlineVertexConsumerProvider() : vertexConsumers
		);
		if (enabled) {
			// Since the target could be different we have to render it now
			shaderTarget.getOutlineVertexConsumerProvider().draw();
		}
	}

}
