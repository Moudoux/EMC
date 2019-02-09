package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.MinecraftClient;

public class IFontRenderer {

    public static void drawString(String text, int x, int y, int color) {
        MinecraftClient.getInstance().fontRenderer.draw(text, x, y, color);
    }

    public static void drawCenteredString(String text, int x, int y, int color) {
        MinecraftClient.getInstance().fontRenderer.drawWithShadow(text, x - MinecraftClient.getInstance().fontRenderer.getStringWidth(text) / 2, y, color);
    }

    public static void drawStringWithShadow(String text, int x, int y, int color) {
        MinecraftClient.getInstance().fontRenderer.drawWithShadow(text, x, y, color);
    }

    public static int getFontHeight() {
        return MinecraftClient.getInstance().fontRenderer.fontHeight;
    }

    public static int getStringWidth(String string) {
        return MinecraftClient.getInstance().fontRenderer.getStringWidth(string);
    }

}
