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

	public QuadRenderStack drawRect(double x, double y, double xx, double yy) {
		return drawRect((float) x, (float) y, (float) xx, (float) yy);
	}

	public QuadRenderStack drawRect(float x, float y, float xx, float yy) {
		// Apply scaling
		if (scaled) {
			x *= getScale();
			y *= getScale();
			xx *= getScale();
			yy *= getScale();
		}
		// Draw
		vertex(xx, y, 0).next();
		vertex(x, y, 0).next();
		vertex(x, yy, 0).next();
		vertex(xx, yy, 0).next();
		return this;
	}

}
