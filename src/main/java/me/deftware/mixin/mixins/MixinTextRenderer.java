package me.deftware.mixin.mixins;

import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public abstract class MixinTextRenderer {

    private static boolean alreadyRendering = false;

    @Shadow protected abstract int draw(String text, float x, float y, int color, Matrix4f matrix, boolean shadow);

    @Shadow public abstract int draw(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light);

    @Shadow protected abstract int drawInternal(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light);

    @Shadow protected abstract float drawLayer(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light);

    @Inject(method = "draw(Ljava/lang/String;FFILnet/minecraft/client/util/math/Matrix4f;Z)I", at = @At("INVOKE"), cancellable = true)
    private void draw(String text, float x, float y, int color, Matrix4f matrix, boolean shadow, CallbackInfoReturnable<Integer> ci) {
        if (!alreadyRendering && shadow) {
            alreadyRendering = true;
            ci.setReturnValue(draw(text, x, y, color, matrix, Bootstrap.EMCSettings.getBool("RENDER_FONT_SHADOWS", true)));
            alreadyRendering = false;
        }
    }

    @Inject(method = "draw(Ljava/lang/String;FFIZLnet/minecraft/client/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I", at = @At("INVOKE"), cancellable = true)
    public void draw(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light, CallbackInfoReturnable<Integer> ci) {
        if (!alreadyRendering && shadow) {
            alreadyRendering = true;
            ci.setReturnValue(draw(text, x, y, color, Bootstrap.EMCSettings.getBool("RENDER_FONT_SHADOWS", true), matrix, vertexConsumerProvider, seeThrough, backgroundColor, light));
            alreadyRendering = false;
        }
    }

    @Inject(method = "drawInternal", at = @At("INVOKE"), cancellable = true)
    private void drawInternal(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light, CallbackInfoReturnable<Integer> ci) {
        if (!alreadyRendering && shadow) {
            alreadyRendering = true;
            ci.setReturnValue(drawInternal(text, x, y, color, Bootstrap.EMCSettings.getBool("RENDER_FONT_SHADOWS", true), matrix, vertexConsumerProvider, seeThrough, backgroundColor, light));
            alreadyRendering = false;
        }
    }

    @Inject(method = "drawLayer", at = @At("INVOKE"), cancellable = true)
    private void drawLayer(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, CallbackInfoReturnable<Float> ci) {
        if (!alreadyRendering && shadow) {
            alreadyRendering = true;
            ci.setReturnValue(drawLayer(text, x, y, color, Bootstrap.EMCSettings.getBool("RENDER_FONT_SHADOWS", true), matrix, vertexConsumerProvider, seeThrough, underlineColor, light));
            alreadyRendering = false;
        }
    }
}
