package me.deftware.client.framework.utils;

import me.deftware.client.framework.wrappers.IResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class TexUtil {

    public static void bindTexture(IResourceLocation texture) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height,
                                                           float textureWidth, float textureHeight) {
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public static int glGenTextures() {
        return GlStateManager.generateTexture();
    }

    public static void deleteTexture(int id) {
        GlStateManager.deleteTexture(id);
    }

}
