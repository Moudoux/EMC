package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.MinecraftClient;

public class IFontRenderer {

    public static void drawString(String text, int x, int y, int color) {
        MinecraftClient.getInstance().textRenderer.draw(text, x, y, color);
    }

    public static void drawCenteredString(String text, int x, int y, int color) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(text, x - MinecraftClient.getInstance().textRenderer.getStringWidth(text) / 2f, y, color);
    }

    public static void drawStringWithShadow(String text, int x, int y, int color) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(text, x, y, color);
    }

    public static int getFontHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    public static int getStringWidth(String string) {
        return MinecraftClient.getInstance().textRenderer.getStringWidth(string);
    }

}
