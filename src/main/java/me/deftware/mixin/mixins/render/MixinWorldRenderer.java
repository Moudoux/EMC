package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.event.events.EventWeather;
import me.deftware.client.framework.render.camera.entity.CameraEntityMan;
import me.deftware.client.framework.render.shader.EntityShader;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "drawEntityOutlinesFramebuffer", at = @At("HEAD"))
    public void drawEntityOutlinesFramebuffer(CallbackInfo ci) {
        if (EntityShader.isActive()) {
            EntityShader.getShader().bind();
            EntityShader.getShader().getProvider().setupUniforms();
        }
    }

    @Inject(method = "drawEntityOutlinesFramebuffer", at = @At("TAIL"))
    public void drawEntityOutlinesFramebufferTail(CallbackInfo ci) {
        if (EntityShader.isActive()) EntityShader.getShader().unbind();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OutlineVertexConsumerProvider;setColor(IIII)V", opcode = 180))
    public void outlineCheck(OutlineVertexConsumerProvider outlineVertexConsumerProvider, int red, int green, int blue, int alpha) {
        if (EntityShader.isActive()) {
            outlineVertexConsumerProvider.setColor(1, 0, 0, 3);
        } else {
            outlineVertexConsumerProvider.setColor(red, green, blue, alpha);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/BlockEntityRenderDispatcher;render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", opcode = 180))
    private void renderBlocKEntity(BlockEntityRenderDispatcher blockEntityRenderDispatcher, BlockEntity blockEntity, float tickDelta, MatrixStack matrix, VertexConsumerProvider original) {
        BlockEntityRenderDispatcher.INSTANCE.render(blockEntity, tickDelta, matrix,
                EntityShader.isStorage() && EntityShader.shouldRun() ? bufferBuilders.getOutlineVertexConsumers() : original
        );
    }

}
