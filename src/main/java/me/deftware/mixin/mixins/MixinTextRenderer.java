package me.deftware.mixin.mixins;

import me.deftware.client.framework.main.bootstrap.Bootstrap;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public abstract class MixinTextRenderer {

    private static boolean alreadyRendering = false;

    @Shadow protected abstract int draw(OrderedText text, float x, float y, int color, Matrix4f matrix, boolean shadow);

    @Shadow public abstract int draw(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light);

    @Shadow protected abstract int drawInternal(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light);

    @Shadow protected abstract int drawInternal(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, boolean seeThrough, int backgroundColor, int light, boolean rightToLeft);

    @Shadow protected abstract float drawLayer(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light);

    @Shadow protected abstract float drawLayer(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light);

    @Inject(method = "draw(Lnet/minecraft/text/OrderedText;FFILnet/minecraft/util/math/Matrix4f;Z)I", at = @At("INVOKE"), cancellable = true)
    private void draw(OrderedText text, float x, float y, int color, Matrix4f matrix, boolean shadow, CallbackInfoReturnable<Integer> ci) {
        if (!alreadyRendering && shadow) {
            alreadyRendering = true;
            ci.setReturnValue(draw(text, x, y, color, matrix, Bootstrap.EMCSettings != null && Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true)));
            alreadyRendering = false;
        }
    }

    @Inject(method = "draw(Lnet/minecraft/text/OrderedText;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I", at = @At("INVOKE"), cancellable = true)
    public void draw(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light, CallbackInfoReturnable<Integer> ci) {
        if (!alreadyRendering && shadow) {
            alreadyRendering = true;
            ci.setReturnValue(draw(text, x, y, color, Bootstrap.EMCSettings != null && Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true), matrix, vertexConsumerProvider, seeThrough, backgroundColor, light));
            alreadyRendering = false;
        }
    }

    @Inject(method = "drawInternal(Ljava/lang/String;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZIIZ)I", at = @At("INVOKE"), cancellable = true)
    private void drawInternalString(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, boolean seeThrough, int backgroundColor, int light, boolean rightToLeft, CallbackInfoReturnable<Integer> ci) {
        if (!alreadyRendering && shadow) {
            alreadyRendering = true;
            ci.setReturnValue(drawInternal(text, x, y, color, Bootstrap.EMCSettings != null && Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true), matrix, vertexConsumers, seeThrough, backgroundColor, light, rightToLeft));
            alreadyRendering = false;
        }
    }

    @Inject(method = "drawInternal(Lnet/minecraft/text/OrderedText;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I", at = @At("INVOKE"), cancellable = true)
    private void drawInternalText(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light, CallbackInfoReturnable<Integer> ci) {
        if (!alreadyRendering && shadow) {
            alreadyRendering = true;
            ci.setReturnValue(drawInternal(text, x, y, color, Bootstrap.EMCSettings != null && Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true), matrix, vertexConsumerProvider, seeThrough, backgroundColor, light));
            alreadyRendering = false;
        }
    }

    @Inject(method = "drawLayer(Lnet/minecraft/text/OrderedText;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)F", at = @At("INVOKE"), cancellable = true)
    private void drawLayerText(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, CallbackInfoReturnable<Float> ci) {
        if (!alreadyRendering && shadow) {
            alreadyRendering = true;
            ci.setReturnValue(drawLayer(text, x, y, color, Bootstrap.EMCSettings != null && Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true), matrix, vertexConsumerProvider, seeThrough, underlineColor, light));
            alreadyRendering = false;
        }
    }

    @Inject(method = "drawLayer(Ljava/lang/String;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)F", at = @At("INVOKE"), cancellable = true)
    private void drawLayerString(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, CallbackInfoReturnable<Float> ci) {
        if (!alreadyRendering && shadow) {
            alreadyRendering = true;
            ci.setReturnValue(drawLayer(text, x, y, color, Bootstrap.EMCSettings != null && Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true), matrix, vertexConsumerProvider, seeThrough, underlineColor, light));
            alreadyRendering = false;
        }
    }
}
