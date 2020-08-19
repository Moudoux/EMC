package me.deftware.client.framework.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.util.math.Matrix4f;

/**
 * @author Deftware
 */
public class GlStateHelper {

	public static void disableAlpha() {
		RenderSystem.disableAlphaTest();
	}

	public static void enableAlpha() {
		RenderSystem.enableAlphaTest();
	}

	public static void enablePolygonOffset() {
		RenderSystem.enablePolygonOffset();
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

	public static void enableLighting() {
		RenderSystem.enableLighting();
	}

	public static void enableBlend() {
		RenderSystem.enableBlend();
	}

	public static void disableBlend() {
		RenderSystem.disableBlend();
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

	public static void disableStandardItemLighting() {
		DiffuseLighting.disable();
	}

	public static void enableStandardItemLighting() {
		DiffuseLighting.enable();
	}

	public static void enableGUIStandardItemLighting() {
		DiffuseLighting.enableForLevel(new Matrix4f());
	}

	public static void disablePolygonOffset() {
		RenderSystem.disablePolygonOffset();
	}

	public static void doPolygonOffset(float f, float g) {
		RenderSystem.polygonOffset(f, g);
	}

}
