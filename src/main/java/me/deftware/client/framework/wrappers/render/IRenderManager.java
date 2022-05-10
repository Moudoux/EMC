package me.deftware.client.framework.wrappers.render;

import me.deftware.mixin.imp.IMixinRenderManager;
import net.minecraft.client.Minecraft;

public class IRenderManager {

	public static double getRenderPosX() {
		return ((IMixinRenderManager) Minecraft.getInstance().getRenderManager()).getRenderPosX();
	}

	public static double getRenderPosY() {
		return ((IMixinRenderManager) Minecraft.getInstance().getRenderManager()).getRenderPosY();
	}

	public static double getRenderPosZ() {
		return ((IMixinRenderManager) Minecraft.getInstance().getRenderManager()).getRenderPosZ();
	}

	public static float getPlayerViewY() {
		return Minecraft.getInstance().getRenderManager().playerViewY;
	}

	public static float getPlayerViewX() {
		return Minecraft.getInstance().getRenderManager().playerViewX;
	}

	public static double getViewerX() {
		return Minecraft.getInstance().getRenderManager().viewerPosX;
	}

	public static double getViewerY() {
		return Minecraft.getInstance().getRenderManager().viewerPosY;
	}

	public static double getViewerZ() {
		return Minecraft.getInstance().getRenderManager().viewerPosZ;
	}

}
