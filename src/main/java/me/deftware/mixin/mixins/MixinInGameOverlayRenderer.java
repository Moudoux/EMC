package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventAnimation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public class MixinInGameOverlayRenderer {
    @Inject(method = "renderInWallOverlay", at = @At("HEAD"), cancellable = true)
    private static void renderInWallOverlay(MinecraftClient minecraftClient, Sprite sprite, MatrixStack matrixStack, CallbackInfo ci) {
        EventAnimation event = new EventAnimation(EventAnimation.AnimationType.Wall);
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderFireOverlay", at = @At("HEAD"), cancellable = true)
    private static void renderFireOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack, CallbackInfo ci) {
        EventAnimation event = new EventAnimation(EventAnimation.AnimationType.Fire);
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderUnderwaterOverlay", at = @At("HEAD"), cancellable = true)
    private static void renderUnderwaterOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack, CallbackInfo ci) {
        EventAnimation event = new EventAnimation(EventAnimation.AnimationType.Underwater);
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
