package me.deftware.client.framework.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.wrappers.IResourceLocation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public class TexUtil {

    private static final MatrixStack stack = new MatrixStack();
    
    public static void bindTexture(IResourceLocation texture) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        DrawableHelper.drawTexture(stack, x, y, u, v, width, height, (int) textureWidth, (int) textureHeight);
    }

    public static int glGenTextures() {
        return GL11.glGenTextures();
    }

    public static void deleteTexture(int id) {
        RenderSystem.deleteTexture(id);
    }

}
