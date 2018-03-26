package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.renderer.RenderHelper;

public class IRenderHelper {

	public static void disableStandardItemLighting() {
		RenderHelper.disableStandardItemLighting();
	}

	public static void enableStandardItemLighting() {
		RenderHelper.enableStandardItemLighting();
	}

	public static void enableGUIStandardItemLighting() {
		RenderHelper.enableGUIStandardItemLighting();
	}

}
