package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.event.events.EventNametagRender;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinRender<T extends Entity> {

    @Unique
    private final EventNametagRender eventNametagRender = new EventNametagRender();

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    private void renderEntityLabel(T entity, Text text, MatrixStack matrixStack, VertexConsumerProvider consumerProvider, int light, CallbackInfo ci) {
        eventNametagRender.create(entity);
        eventNametagRender.broadcast();
        if (eventNametagRender.isCanceled()) {
            ci.cancel();
        }
    }

}
