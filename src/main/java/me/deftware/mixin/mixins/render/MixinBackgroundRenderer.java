package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.event.events.EventFogRender;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        EventFogRender event = new EventFogRender(camera, fogType, viewDistance, thickFog);
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}