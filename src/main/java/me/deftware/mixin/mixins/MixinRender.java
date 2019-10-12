package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventNametagRender;
import net.minecraft.client.render.LayeredVertexConsumerStorage;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinRender<T extends Entity> {

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    private void renderEntityLabel(T entity_1, String string_1, MatrixStack matrixStack_1, LayeredVertexConsumerStorage layeredVertexConsumerStorage_1, CallbackInfo ci) {
        EventNametagRender event = new EventNametagRender(entity_1);
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

}
