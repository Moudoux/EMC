package me.deftware.client.framework.fonts.cfont;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static me.deftware.client.framework.wrappers.render.IGL11.glLineWidth;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class FontUtils {

	private static final Tessellator tessellator = Tessellator.getInstance();

	public static void drawTextureRect(float x, float y, float width, float height, float u, float v, float t, float s) {
		BufferBuilder renderer = tessellator.getBuffer();
		renderer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
		renderer.pos(x + width, y, 0F).tex(t, v).endVertex();
		renderer.pos(x, y, 0F).tex(u, v).endVertex();
		renderer.pos(x, y + height, 0F).tex(u, s).endVertex();
		renderer.pos(x, y + height, 0F).tex(u, s).endVertex();
		renderer.pos(x + width, y + height, 0F).tex(t, s).endVertex();
		renderer.pos(x + width, y, 0F).tex(t, v).endVertex();
		tessellator.draw();
	}

	public static void drawTextureRect(float x, float y, float width, float height, float u, float v, float t, float s, float z) {
		BufferBuilder renderer = tessellator.getBuffer();
		renderer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
		renderer.pos(x + width, y, z).tex(t, v).endVertex();
		renderer.pos(x, y, z).tex(u, v).endVertex();
		renderer.pos(x, y + height, z).tex(u, s).endVertex();
		renderer.pos(x, y + height, z).tex(u, s).endVertex();
		renderer.pos(x + width, y + height, z).tex(t, s).endVertex();
		renderer.pos(x + width, y, z).tex(t, v).endVertex();
		tessellator.draw();
	}

	/**
	 * Renders a line from the given x, y positions to the second x1, y1 positions.
	 */
	public static void drawLine(float size, float x, float y, float x1, float y1) {
		glLineWidth(size);
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		BufferBuilder vertexBuffer = tessellator.getBuffer();
		vertexBuffer.begin(GL_LINES, DefaultVertexFormats.POSITION);
		vertexBuffer.pos(x, y, 0F).endVertex();
		vertexBuffer.pos(x1, y1, 0F).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
	}

}
