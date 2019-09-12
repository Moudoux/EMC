package me.deftware.client.framework.wrappers.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.DrawableHelper;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class IGlStateManager {

    public static void rotate(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x, y, z);
    }

    public static void scale(float x, float y, float z) {
        GL11.glScalef(x, y, z);
    }

    public static void scale(double x, double y, double z) {
        GL11.glScaled(x, y, z);
    }

    public static void translate(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }

    public static void translate(double x, double y, double z) {
        GL11.glTranslated(x, y, z);
    }

    public static void multMatrix(FloatBuffer matrix) {
        GL11.glMultMatrixf(matrix);
    }

    public static void color(float colorRed, float colorGreen, float colorBlue) {
        GlStateManager.color4f(colorRed, colorGreen, colorBlue, 1.0F);
    }

    public static void resetColor() {
        GlStateManager.clearCurrentColor();
    }

    public static void pushMatrix() {
        GlStateManager.pushMatrix();
    }

    public static void popMatrix() {
        GlStateManager.popMatrix();
    }

    public static void enableDepth() {
        GlStateManager.enableDepthTest();
    }

    public static void disableDepth() {
        GlStateManager.disableDepthTest();
    }

    public static void disableLighting() {
        GlStateManager.disableLighting();
    }

    public static void enableBlend() {
        GlStateManager.enableBlend();
    }

    public static void disableTexture2D() {
        GlStateManager.disableTexture();
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        GlStateManager.blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
    }

    public static void enableTexture2D() {
        GlStateManager.enableTexture();
    }

    public static void enableLighting() {
        GlStateManager.enableLighting();
    }

    public static void disableBlend() {
        GlStateManager.disableBlend();
    }

    public static void enableRescaleNormal() {
        GlStateManager.enableRescaleNormal();
    }

    public static void disableRescaleNormal() {
        GlStateManager.disableRescaleNormal();
    }

    public static void disablePolygonOffset() {
        GlStateManager.disablePolygonOffset();
    }

    public static void doPolygonOffset(float f, float g) {
        GlStateManager.polygonOffset(f, g);
    }

    public static void disableAlpha() {
        GlStateManager.disableAlphaTest();
    }

    public static void enableAlpha() {
        GlStateManager.enableAlphaTest();
    }

    public static void enablePolygonOffset() {
        GlStateManager.enablePolygonOffset();
    }

}
