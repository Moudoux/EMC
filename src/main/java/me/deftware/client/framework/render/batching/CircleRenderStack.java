package me.deftware.client.framework.render.batching;

import org.lwjgl.opengl.GL11;

/**
 * @author Deftware
 */
public class CircleRenderStack extends RenderStack<CircleRenderStack> {

	@Override
	public CircleRenderStack begin() {
		return begin(GL11.GL_TRIANGLE_FAN);
	}

	public CircleRenderStack drawFilledCircle(float xx, float yy, float radius, boolean scaling) {
		if (scaling) {
			xx *= getScale();
			yy *= getScale();
			radius *= getScale();
		}
		for (int i = 0; i < 50; i++) {
			float x = (float) (radius * Math.sin(i * 0.12566370614359174D));
			float y = (float) (radius * Math.cos(i * 0.12566370614359174D));
			vertex(xx + x, yy + y, 0).color(red, green, blue, alpha).next();
		}
		return this;
	}

}
