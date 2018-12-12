package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.Minecraft;

public class IFontRenderer {

	public static void drawString(String text, int x, int y, int color) {
		Minecraft.getInstance().fontRenderer.drawString(text, x, y, color);
	}

	public static void drawCenteredString(String text, int x, int y, int color) {
		Minecraft.getInstance().fontRenderer.drawStringWithShadow(text, x - Minecraft.getInstance().fontRenderer.getStringWidth(text) / 2, y, color);
	}

	public static void drawStringWithShadow(String text, int x, int y, int color) {
		Minecraft.getInstance().fontRenderer.drawStringWithShadow(text, x, y, color);
	}

	public static int getFontHeight() {
		return Minecraft.getInstance().fontRenderer.FONT_HEIGHT;
	}

	public static int getStringWidth(String string) {
		return Minecraft.getInstance().fontRenderer.getStringWidth(string);
	}

}
