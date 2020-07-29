package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class IFontRenderer {

    public static void drawString(String text, int x, int y, int color) {
        drawString(new MatrixStack(), text, x, y, color);
    }

    public static void drawString(MatrixStack matrixStack, String text, int x, int y, int color) {
        MinecraftClient.getInstance().textRenderer.draw(matrixStack, text, x, y, color);
    }

    public static void drawCenteredString(String text, int x, int y, int color) {
        drawCenteredString(new MatrixStack(), text, x, y, color);
    }

    public static void drawCenteredString(MatrixStack matrixStack, String text, int x, int y, int color) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, text, x - MinecraftClient.getInstance().textRenderer.getWidth(text) / 2f, y, color);
    }

    public static void drawStringWithShadow(String text, int x, int y, int color) {
        drawStringWithShadow(new MatrixStack(), text, x, y, color);
    }

    public static void drawStringWithShadow(MatrixStack matrixStack, String text, int x, int y, int color) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, text, x, y, color);
    }

    public static int getFontHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    public static int getStringWidth(String string) {
        return MinecraftClient.getInstance().textRenderer.getWidth(string);
    }

}
