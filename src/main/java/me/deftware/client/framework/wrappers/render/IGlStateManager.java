package me.deftware.client.framework.wrappers.render;

import com.mojang.blaze3d.systems.RenderSystem;
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
        RenderSystem.color4f(colorRed, colorGreen, colorBlue, 1.0F);
    }

    public static void resetColor() {
        RenderSystem.clearCurrentColor();
    }

    public static void pushMatrix() {
        RenderSystem.pushMatrix();
    }

    public static void popMatrix() {
        RenderSystem.popMatrix();
    }

    public static void enableDepth() {
        RenderSystem.enableDepthTest();
    }

    public static void disableDepth() {
        RenderSystem.disableDepthTest();
    }

    public static void disableLighting() {
        RenderSystem.disableLighting();
    }

    public static void enableBlend() {
        RenderSystem.enableBlend();
    }

    public static void disableTexture2D() {
        RenderSystem.disableTexture();
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        RenderSystem.blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
    }

    public static void enableTexture2D() {
        RenderSystem.enableTexture();
    }

    public static void enableLighting() {
        RenderSystem.enableLighting();
    }

    public static void disableBlend() {
        RenderSystem.disableBlend();
    }

    public static void enableRescaleNormal() {
        RenderSystem.enableRescaleNormal();
    }

    public static void disableRescaleNormal() {
        RenderSystem.disableRescaleNormal();
    }

    public static void disablePolygonOffset() {
        RenderSystem.disablePolygonOffset();
    }

    public static void doPolygonOffset(float f, float g) {
        RenderSystem.polygonOffset(f, g);
    }

    public static void disableAlpha() {
        RenderSystem.disableAlphaTest();
    }

    public static void enableAlpha() {
        RenderSystem.enableAlphaTest();
    }

    public static void enablePolygonOffset() {
        RenderSystem.enablePolygonOffset();
    }
    public static void lineWidth(float f) {
         RenderSystem.lineWidth(f);
    }

}
