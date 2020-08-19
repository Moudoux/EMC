package me.deftware.client.framework.fonts.minecraft;

import me.deftware.client.framework.chat.ChatMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

/**
 * @author Deftware
 */
public class FontRenderer {

	private static final MatrixStack stack = new MatrixStack();

	public static void drawString(ChatMessage text, int x, int y, int color) {
		MinecraftClient.getInstance().textRenderer.draw(stack, text.build(), x, y, color);
	}

	public static void drawCenteredString(ChatMessage text, int x, int y, int color) {
		LiteralText compiled = text.build();
		MinecraftClient.getInstance().textRenderer.drawWithShadow(stack, compiled, x - MinecraftClient.getInstance().textRenderer.getWidth(compiled) / 2f, y, color);
	}

	public static void drawStringWithShadow(ChatMessage text, int x, int y, int color) {
		MinecraftClient.getInstance().textRenderer.drawWithShadow(stack, text.build(), x, y, color);
	}

	public static int getFontHeight() {
		return MinecraftClient.getInstance().textRenderer.fontHeight;
	}

	public static int getStringWidth(ChatMessage string) {
		return MinecraftClient.getInstance().textRenderer.getWidth(string.build());
	}

	public static int getStringWidth(String string) {
		return MinecraftClient.getInstance().textRenderer.getWidth(string);
	}

}
