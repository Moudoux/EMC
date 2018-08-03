package me.deftware.client.framework.wrappers.render;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;

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
		GL11.glMultMatrix(matrix);
	}

	public static void color(float colorRed, float colorGreen, float colorBlue) {
		GlStateManager.color(colorRed, colorGreen, colorBlue, 1.0F);
	}

	public static void resetColor() {
		GlStateManager.resetColor();
	}

	public static void pushMatrix() {
		GlStateManager.pushMatrix();
	}

	public static void popMatrix() {
		GlStateManager.popMatrix();
	}

	public static void enableDepth() {
		GlStateManager.enableDepth();
	}

	public static void disableDepth() {
		GlStateManager.disableDepth();
	}

	public static void disableLighting() {
		GlStateManager.disableLighting();
	}

	public static void enableBlend() {
		GlStateManager.enableBlend();
	}

	public static void disableTexture2D() {
		GlStateManager.disableTexture2D();
	}

	public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
		GlStateManager.tryBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
	}

	public static void enableTexture2D() {
		GlStateManager.enableTexture2D();
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
		GlStateManager.doPolygonOffset(f, g);
	}

	public static void disableAlpha() {
		GlStateManager.disableAlpha();
	}

	public static void enableAlpha() {
		GlStateManager.enableAlpha();
	}

	public static void enablePolygonOffset() {
		GlStateManager.enablePolygonOffset();
	}

}
