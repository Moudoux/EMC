package me.deftware.client.framework.fonts.minecraft;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.render.gl.GLX;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Matrix4f;

/**
 * @author Deftware
 */
public class FontRenderer {

	public static void drawString(ChatMessage text, int x, int y, int color) {
		MinecraftClient.getInstance().textRenderer.draw(getStack(), text.build(), x, y, color);
	}

	public static void drawCenteredString(ChatMessage text, int x, int y, int color) {
		LiteralText compiled = text.build();
		MinecraftClient.getInstance().textRenderer.drawWithShadow(getStack(), compiled, x - MinecraftClient.getInstance().textRenderer.getWidth(compiled) / 2f, y, color);
	}

	public static void drawStringWithShadow(ChatMessage text, int x, int y, int color) {
		MinecraftClient.getInstance().textRenderer.drawWithShadow(getStack(), text.build(), x, y, color);
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

	private static MatrixStack getStack() {
		return GLX.INSTANCE.getStack();
	}

}
