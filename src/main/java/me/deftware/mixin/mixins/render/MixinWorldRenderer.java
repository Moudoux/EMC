package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.event.events.EventWeather;
import me.deftware.client.framework.render.camera.entity.CameraEntityMan;
import me.deftware.client.framework.render.shader.EntityShader;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer {

    @Shadow @Final private BufferBuilderStorage bufferBuilders;

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

    @Inject(method = "drawEntityOutlinesFramebuffer", at = @At("HEAD"))
    public void drawEntityOutlinesFramebuffer(CallbackInfo ci) {
        if (EntityShader.shouldRun()) {
            EntityShader.getShader().bind();
            EntityShader.getShader().getProvider().setupUniforms();
        }
    }

    @Redirect(method = "drawEntityOutlinesFramebuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;canDrawEntityOutlines()Z", opcode = 180))
    private boolean canDrawEntityOutlinesBuffer(WorldRenderer worldRenderer) {
        return true;
    }

    @Inject(method = "drawEntityOutlinesFramebuffer", at = @At("TAIL"))
    public void drawEntityOutlinesFramebufferTail(CallbackInfo ci) {
        if (EntityShader.shouldRun()) EntityShader.getShader().unbind();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/BlockEntityRenderDispatcher;render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", opcode = 180))
    private void renderBlocKEntity(BlockEntityRenderDispatcher blockEntityRenderDispatcher, BlockEntity blockEntity, float tickDelta, MatrixStack matrix, VertexConsumerProvider original) {
        BlockEntityRenderDispatcher.INSTANCE.render(blockEntity, tickDelta, matrix,
                EntityShader.isStorage() && EntityShader.shouldRun() && (blockEntity instanceof LootableContainerBlockEntity || blockEntity instanceof EnderChestBlockEntity) ? bufferBuilders.getOutlineVertexConsumers() : original
        );
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;canDrawEntityOutlines()Z", opcode = 180, ordinal = 0))
    private boolean canDrawEntityOutlines(WorldRenderer worldRenderer) {
        return true;
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderEntity(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"))
    private void modifyRenderEntity(Args arguments) {
        Entity entity = arguments.get(0);
        if (EntityShader.shouldRun()) {
            if ((entity instanceof ItemEntity && EntityShader.isItems()) || EntityShader.getEntityPredicate().test(entity)) {
                arguments.set(6, bufferBuilders.getOutlineVertexConsumers());
            }
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/ShaderEffect;render(F)V", opcode = 180))
    private void onRenderShader(ShaderEffect shaderEffect, float tickDelta) {
        if (!EntityShader.shouldRun()) {
            shaderEffect.render(tickDelta);
        }
    }

}
