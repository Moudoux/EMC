package me.deftware.client.framework.wrappers.render;

import me.deftware.client.framework.fonts.cfont.CFont;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

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

	public static void drawStringWithShadow(String text, float x, float y, CFont font) {
		font.drawStringWithShadow(text, (int) x, (int) y, 0xFFFFFF);
	}

	public static void drawStringWithShadow(String text, float x, float y, int color, CFont font) {
		font.drawStringWithShadow(text, (int) x, (int) y, color);
	}

	public static int getFontHeight(CFont font) {
		return font.getHeight();
	}

	public static int getStringWidth(String string, CFont font) {
		return font.getStringWidth(string);
	}

}
