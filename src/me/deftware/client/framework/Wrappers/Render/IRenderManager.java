package me.deftware.client.framework.Wrappers.Render;

import net.minecraft.client.Minecraft;

public class IRenderManager {

	public static double getRenderPosX() {
		return Minecraft.getMinecraft().getRenderManager().renderPosX;
	}
	
	public static double getRenderPosY() {
		return Minecraft.getMinecraft().getRenderManager().renderPosY;
	}
	
	public static double getRenderPosZ() {
		return Minecraft.getMinecraft().getRenderManager().renderPosZ;
	}
	
	public static float getPlayerViewY() {
		return Minecraft.getMinecraft().getRenderManager().playerViewY;
	}
	
	public static float getPlayerViewX() {
		return Minecraft.getMinecraft().getRenderManager().playerViewX;
	}
	
}
