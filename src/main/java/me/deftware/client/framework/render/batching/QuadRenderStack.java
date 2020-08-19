package me.deftware.client.framework.render.batching;

import org.lwjgl.opengl.GL11;

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
		// Draw
		GL11.glVertex2f(xx, y);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, yy);
		GL11.glVertex2f(xx, yy);
		return this;
	}

}
