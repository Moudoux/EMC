package me.deftware.client.framework.render.batching.minecraft;

import me.deftware.client.framework.render.batching.RenderStack;
import org.lwjgl.opengl.GL11;

/**
 * @author Deftware
 */
public class IngameCircleRenderStack extends RenderStack<IngameCircleRenderStack> {

	@Override
	public IngameCircleRenderStack begin() {
		return begin(GL11.GL_TRIANGLE_FAN);
	}

	@Override
	public IngameCircleRenderStack setupMatrix(int matrixWidth, int matrixHeight) {
		GL11.glPushMatrix();
		// Setup gl
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		return this;
	}

	@Override
	public void end() {
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public IngameCircleRenderStack drawFilledCircle(int xx, int yy, float radius) {
		for (int i = 0; i < 50; i++) {
			float x = (float) (radius + 1 * Math.sin(i * 0.12566370614359174D));
			float y = (float) (radius + 1 * Math.cos(i * 0.12566370614359174D));
			GL11.glVertex2f(xx + x, yy + y);
		}
		return this;
	}

}
