package me.deftware.client.framework.utils.render;

import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.client.framework.wrappers.IResourceLocation;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class TexUtil {

	public static void bindTexture(IResourceLocation texture) {
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
	}

	public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height,
														   float textureWidth, float textureHeight) {
		Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
	}

	public static void drawTexturedModalRect(int p_drawTexturedModalRect_1_, int p_drawTexturedModalRect_2_, int p_drawTexturedModalRect_3_, int p_drawTexturedModalRect_4_, int p_drawTexturedModalRect_5_, int p_drawTexturedModalRect_6_) {

	}

	public static int glGenTextures() {
		return GlStateManager.generateTexture();
	}

	public static void deleteTexture(int id) {
		GlStateManager.deleteTexture(id);
	}
}
