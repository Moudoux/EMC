package me.deftware.client.framework.utils;

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

	public static int glGenTextures() {
		return GlStateManager.generateTexture();
	}

	public static void deleteTexture(int id) {
		GlStateManager.deleteTexture(id);
	}

	public static void prepareAndPushMatrix() {
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST); //3008 alpha test
		GL11.glPushMatrix();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); //Transparency blending
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, IGuiScreen.getDisplayWidth(), IGuiScreen.getDisplayHeight(), 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	public static void renderAndPopMatrix(int x, int y, int width, int height) {
		//Draw quad counterclockwise
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2d(x, y); //top-left
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2d(x, y + height); //down-left aka height
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2d(x + width, y + height); //down-right aka width
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2d(x + width, y); //top-right
		}
		GL11.glEnd();

		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);

		IMinecraft.triggerGuiRenderer();
	}
}
