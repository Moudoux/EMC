package me.deftware.client.framework.render.batching;

import me.deftware.client.framework.gui.GuiScreen;
import me.deftware.client.framework.render.shader.Shader;
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
		return drawRect(x, y, xx, yy, scaling, null);
	}

	public QuadRenderStack drawRect(double x, double y, double xx, double yy, boolean scaling, Shader shader) {
		// Apply scaling
		if (scaling) {
			x *= getScale();
			y *= getScale();
			xx *= getScale();
			yy *= getScale();
		}
		if (shader != null) setupUniforms((float) x, (float) y, (float) (xx - x), (float) (yy - y), shader);
		// Draw
		GL11.glVertex2d(xx, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, yy);
		GL11.glVertex2d(xx, yy);
		return this;
	}

	public QuadRenderStack drawRect(float x, float y, float xx, float yy, boolean scaling) {
		return drawRect(x, y, xx, yy, scaling, null);
	}

	public QuadRenderStack drawRect(float x, float y, float xx, float yy, boolean scaling, Shader shader) {
		// Apply scaling
		if (scaling) {
			x *= getScale();
			y *= getScale();
			xx *= getScale();
			yy *= getScale();
		}
		if (shader != null) setupUniforms(x, y, xx - x, yy - y, shader);
		// Draw
		GL11.glVertex2f(xx, y);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, yy);
		GL11.glVertex2f(xx, yy);
		return this;
	}
	
	public void setupUniforms(float x, float y, float width, float height, Shader shader) {
		int resolution = GL20.glGetUniformLocation(shader.getProgram(), "resolution"),
			coordinates = GL20.glGetUniformLocation(shader.getProgram(), "coordinates");
		GL20.glUniform4f(resolution, width, height, GuiScreen.getDisplayWidth(), GuiScreen.getDisplayHeight());
		GL20.glUniform2f(coordinates, x, y);
		GL11.glBegin(GL11.GL_QUADS);
		running = true;
	}

}
