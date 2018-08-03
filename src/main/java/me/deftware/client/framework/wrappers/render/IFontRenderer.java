package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.Minecraft;

public class IFontRenderer {

	public static void drawString(String text, int x, int y, int color) {
		Minecraft.getMinecraft().fontRenderer.func_211126_b(text, x, y, color);
	}

	public static void drawCenteredString(String text, int x, int y, int color) {
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x - Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2, y, color);
	}

	public static void drawStringWithShadow(String text, int x, int y, int color) {
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, color);
	}

	public static int getFontHeight() {
		return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
	}

	public static int getStringWidth(String string) {
		return Minecraft.getMinecraft().fontRenderer.getStringWidth(string);
	}

}
