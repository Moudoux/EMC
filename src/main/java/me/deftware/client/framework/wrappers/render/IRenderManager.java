package me.deftware.client.framework.wrappers.render;

import me.deftware.mixin.imp.IMixinRenderManager;
import net.minecraft.client.Minecraft;

public class IRenderManager {

	public static double getRenderPosX() {
		return ((IMixinRenderManager) Minecraft.getMinecraft().getRenderManager()).getRenderPosX();
	}

	public static double getRenderPosY() {
		return ((IMixinRenderManager) Minecraft.getMinecraft().getRenderManager()).getRenderPosY();
	}

	public static double getRenderPosZ() {
		return ((IMixinRenderManager) Minecraft.getMinecraft().getRenderManager()).getRenderPosZ();
	}

	public static float getPlayerViewY() {
		return Minecraft.getMinecraft().getRenderManager().playerViewY;
	}

	public static float getPlayerViewX() {
		return Minecraft.getMinecraft().getRenderManager().playerViewX;
	}

	public static double getViewerX() {
		return Minecraft.getMinecraft().getRenderManager().viewerPosX;
	}

	public static double getViewerY() {
		return Minecraft.getMinecraft().getRenderManager().viewerPosY;
	}

	public static double getViewerZ() {
		return Minecraft.getMinecraft().getRenderManager().viewerPosZ;
	}

}
