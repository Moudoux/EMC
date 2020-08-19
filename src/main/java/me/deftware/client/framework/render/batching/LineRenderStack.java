package me.deftware.client.framework.render.batching;

import org.lwjgl.opengl.GL11;

/**
 * @author Deftware
 */
public class LineRenderStack extends RenderStack<LineRenderStack> {

	@Override
	public LineRenderStack begin() {
		return begin(GL11.GL_LINES);
	}

	public LineRenderStack drawLine(float x1, float y1, float x2, float y2, boolean scaling) {
		// Scale
		if (scaling) {
			x1 *= getScale();
			y1 *= getScale();
			x2 *= getScale();
			y2 *= getScale();
		}
		// Draw
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x2, y2);
		return this;
	}

}
