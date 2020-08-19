package me.deftware.client.framework.render.batching.minecraft;

import me.deftware.client.framework.render.batching.RenderStack;
import org.lwjgl.opengl.GL11;

/**
 * @author Deftware
 */
public class IngameQuadRenderStack extends RenderStack<IngameQuadRenderStack> {

	@Override
	public IngameQuadRenderStack begin() {
		return begin(GL11.GL_QUADS);
	}

	@Override
	public IngameQuadRenderStack setupMatrix(int matrixWidth, int matrixHeight) {
		// Setup gl
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glPushMatrix();
		return this;
	}

	public IngameQuadRenderStack drawRect(float x, float y, float xx, float yy) {
		GL11.glVertex2d(xx, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, yy);
		GL11.glVertex2d(xx, yy);
		return this;
	}

	public void end() {
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
