package me.deftware.client.framework.Wrappers.Render;

import me.deftware.client.framework.FontRender.IFontRendererObject;
import net.minecraft.client.Minecraft;

public class IFontRenderer {
	
	public static void drawString(String text, int x, int y, int color) {
		Minecraft.getMinecraft().fontRendererObj.drawString(text,
				x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2, y, color);
	}
	
	public static void drawCenteredString(String text, int x, int y, int color) {
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2, y, color);
	}
	
	public static void drawStringWithShadow(String text, int x, int y, int color) {
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x, y, color);
	}
	
	public static int getFontHeight() {
		return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}
	
	public static int getStringWidth(String string) {
		return Minecraft.getMinecraft().fontRendererObj.getStringWidth(string);
	}
	
	public static void drawStringWithShadow(String text, int x, int y, IFontRendererObject font) {
		font.drawStringWithShadow(text, x, y, 0xFFFFFF);
	}
	
	public static void drawStringWithShadow(String text, int x, int y, int color, IFontRendererObject font) {
		font.drawStringWithShadow(text, x, y, color);
	}
	
	public static int getFontHeight(IFontRendererObject font) {
		return font.FONT_HEIGHT;
	}
	
	public static int getStringWidth(String string, IFontRendererObject font) {
		return font.getStringWidth(string);
	}

	public static int getCharWidth(char character) {
		return Minecraft.getMinecraft().fontRendererObj.getCharWidth(character);
	}

}
