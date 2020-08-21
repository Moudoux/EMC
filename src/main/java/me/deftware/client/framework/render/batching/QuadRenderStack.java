package me.deftware.client.framework.render.batching;

import me.deftware.client.framework.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * @author Deftware
 */
public class QuadRenderStack extends RenderStack<QuadRenderStack> {

	@Override
	public QuadRenderStack begin() {
		return begin(GL11.GL_QUADS);
	}

	public QuadRenderStack drawRect(double x, double y, double xx, double yy, boolean scaling) {
		// Apply scaling
		if (scaling) {
			x *= getScale();
			y *= getScale();
			xx *= getScale();
			yy *= getScale();
		}
		if (shader != null) setupUniforms((float) (x + xx), (float) (y + yy));
		// Draw
		GL11.glVertex2d(xx, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, yy);
		GL11.glVertex2d(xx, yy);
		return this;
	}

	public QuadRenderStack drawRect(float x, float y, float xx, float yy, boolean scaling) {
		// Apply scaling
		if (scaling) {
			x *= getScale();
			y *= getScale();
			xx *= getScale();
			yy *= getScale();
		}
		if (shader != null) setupUniforms(x + xx, y + yy);
		// Draw
		GL11.glVertex2f(xx, y);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, yy);
		GL11.glVertex2f(xx, yy);
		return this;
	}
	
	public void setupUniforms(float width, float height) {
		int resolution = GL20.glGetUniformLocation(shader.getProgram(), "resolution");
		// Y axis is inverted in glsl shaders so we have to pass both the height and screen height
		GL20.glUniform3f(resolution, width, GuiScreen.getDisplayHeight(), height);
		GL11.glBegin(GL11.GL_QUADS);
	}

}
