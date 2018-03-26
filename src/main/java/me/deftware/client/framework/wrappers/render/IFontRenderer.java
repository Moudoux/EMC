package me.deftware.client.framework.wrappers.render;

import me.deftware.client.framework.fonts.IFontRendererObject;
import net.minecraft.client.Minecraft;

public class IFontRenderer {

	public static void drawString(String text, int x, int y, int color) {
		Minecraft.getMinecraft().fontRenderer.drawString(text, x, y, color);
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

	public static void drawStringWithShadow(String text, float x, float y, IFontRendererObject font) {
		font.drawStringWithShadow(text, (int) x, (int) y, 0xFFFFFF);
	}

	public static void drawStringWithShadow(String text, float x, float y, int color, IFontRendererObject font) {
		font.drawStringWithShadow(text, (int) x, (int) y, color);
	}

	public static int getFontHeight(IFontRendererObject font) {
		return font.FONT_HEIGHT;
	}

	public static int getStringWidth(String string, IFontRendererObject font) {
		return font.getStringWidth(string);
	}

	public static int getCharWidth(char character) {
		return Minecraft.getMinecraft().fontRenderer.getCharWidth(character);
	}

}
