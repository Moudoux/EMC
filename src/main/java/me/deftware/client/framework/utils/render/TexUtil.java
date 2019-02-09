package me.deftware.client.framework.utils.render;

import com.mojang.blaze3d.platform.GlStateManager;
import me.deftware.client.framework.wrappers.IResourceLocation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;

public class TexUtil {

    public static void bindTexture(IResourceLocation texture) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        Drawable.drawTexturedRect(x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public static int glGenTextures() {
        return GlStateManager.genTexture();
    }

    public static void deleteTexture(int id) {
        GlStateManager.deleteTexture(id);
    }

}
