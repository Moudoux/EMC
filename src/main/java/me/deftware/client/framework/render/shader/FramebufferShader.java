package me.deftware.client.framework.render.shader;

import me.deftware.client.framework.gui.GuiScreen;
import net.minecraft.client.gl.Framebuffer;
import org.lwjgl.opengl.GL11;

/**
 * TODO
 *
 * @author Deftware
 */
public class FramebufferShader {

	private Framebuffer framebuffer;
	private final Shader shader;

	public FramebufferShader(Shader shader) {
		this.shader = shader;
	}

	public void begin(float partialTicks) {
		if (framebuffer != null) {
			framebuffer.delete();
		}
		// Set up buffer
		framebuffer = new Framebuffer(GuiScreen.getDisplayWidth(), GuiScreen.getDisplayHeight(), true, false);
		framebuffer.clear(false);
		framebuffer.beginWrite(true);
	}


	public void end() {
		drawFramebuffer();
	}

	public void drawFramebuffer() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffer.getColorAttachment());
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(0, 0);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(0, GuiScreen.getScaledHeight());
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(GuiScreen.getScaledWidth(), GuiScreen.getScaledHeight());
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(GuiScreen.getScaledWidth(), 0);
		GL11.glEnd();
	}

}
