package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventNametagRender;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinRender<T extends Entity> {

    @Inject(method = "renderEntityLabel", at = @At("HEAD"), cancellable = true)
    private void renderEntityLabel(T entityIn, String str, double x, double y, double z, int maxDistance,
                                   CallbackInfo cb) {
        EventNametagRender event = new EventNametagRender(entityIn).send();
        if (event.isCanceled()) {
            cb.cancel();
        }
    }

}
