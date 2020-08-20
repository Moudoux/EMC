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

	@Override
	public CircleRenderStack setupMatrix(int matrixWidth, int matrixHeight) {
		GL11.glPushMatrix();
		reloadCustomMatrix(matrixWidth, matrixHeight);
		// Setup gl
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		return this;
	}

	public CircleRenderStack drawFilledCircle(float xx, float yy, float radius, boolean scaling) {
		if (scaling) {
			xx *= getScale();
			yy *= getScale();
			radius *= getScale();
		}
		for (int i = 0; i < 50; i++) {
			float x = (float) (radius + 1 * Math.sin(i * 0.12566370614359174D));
			float y = (float) (radius + 1 * Math.cos(i * 0.12566370614359174D));
			GL11.glVertex2f(xx + x, yy + y);
		}
		for (int i = 0; i < 50; i++) {
			float x = (float) (radius * Math.sin(i * 0.12566370614359174D));
			float y = (float) (radius * Math.cos(i * 0.12566370614359174D));
			GL11.glVertex2f(xx + x, yy + y);
		}
		return this;
	}

}
