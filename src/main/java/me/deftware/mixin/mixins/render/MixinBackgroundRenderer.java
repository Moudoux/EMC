package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.event.events.EventFogRender;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {

    @Unique
    private static final EventFogRender eventFogRender = new EventFogRender();

    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        eventFogRender.create(camera, fogType, viewDistance, thickFog);
        eventFogRender.broadcast();
        if (eventFogRender.isCanceled()) {
            ci.cancel();
        }
    }

}