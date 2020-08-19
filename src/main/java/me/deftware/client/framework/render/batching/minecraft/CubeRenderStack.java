package me.deftware.client.framework.render.batching.minecraft;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.helper.RenderHelper;
import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.render.batching.RenderStack;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.Box;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

/**
 * @author Deftware
 */
public class CubeRenderStack extends RenderStack<CubeRenderStack> {

	@Override
	public CubeRenderStack begin() {
		return this; /* Not used in this stack */
	}

	@Override
	public CubeRenderStack setupMatrix(int matrixWidth, int matrixHeight) {
		RenderSystem.clearCurrentColor();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(lineWidth);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		return this;
	}

	@Override
	public void end() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
		RenderSystem.clearCurrentColor();
	}

	public CubeRenderStack ESPBox(BoundingBox box) {
		drawColorBox(box.getOffsetMinecraftBox(-RenderHelper.getRenderPosX(), -RenderHelper.getRenderPosY(), -RenderHelper.getRenderPosZ()));
		return this;
	}

	public CubeRenderStack emptyESPBox(BoundingBox box) {
		drawSelectionBoundingBox(box.getOffsetMinecraftBox(-RenderHelper.getRenderPosX(), -RenderHelper.getRenderPosY(), -RenderHelper.getRenderPosZ()));
		return this;
	}

	private void drawColorBox(Box box) {
		float T1 = 0, T2 = 0;
		Tessellator ts = Tessellator.getInstance();
		BufferBuilder vb = ts.getBuffer();
		vb.begin(7, VertexFormats.POSITION_TEXTURE);
		vb.vertex(box.minX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		ts.draw();
		vb.begin(7, VertexFormats.POSITION_TEXTURE);
		vb.vertex(box.maxX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		ts.draw();
		vb.begin(7, VertexFormats.POSITION_TEXTURE);
		vb.vertex(box.minX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		ts.draw();
		vb.begin(7, VertexFormats.POSITION_TEXTURE);
		vb.vertex(box.minX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		ts.draw();
		vb.begin(7, VertexFormats.POSITION_TEXTURE);
		vb.vertex(box.minX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		ts.draw();
		vb.begin(7, VertexFormats.POSITION_TEXTURE);
		vb.vertex(box.minX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.minX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.minZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.maxY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		vb.vertex(box.maxX, box.minY, box.maxZ).texture(T1, T2).color(0F, 0F, 0F, 0F).next();
		ts.draw();
	}

	private void drawSelectionBoundingBox(Box box) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(3, VertexFormats.POSITION);
		vertexbuffer.vertex(box.minX, box.minY, box.minZ).next();
		vertexbuffer.vertex(box.maxX, box.minY, box.minZ).next();
		vertexbuffer.vertex(box.maxX, box.minY, box.maxZ).next();
		vertexbuffer.vertex(box.minX, box.minY, box.maxZ).next();
		vertexbuffer.vertex(box.minX, box.minY, box.minZ).next();
		tessellator.draw();
		vertexbuffer.begin(3, VertexFormats.POSITION);
		vertexbuffer.vertex(box.minX, box.maxY, box.minZ).next();
		vertexbuffer.vertex(box.maxX, box.maxY, box.minZ).next();
		vertexbuffer.vertex(box.maxX, box.maxY, box.maxZ).next();
		vertexbuffer.vertex(box.minX, box.maxY, box.maxZ).next();
		vertexbuffer.vertex(box.minX, box.maxY, box.minZ).next();
		tessellator.draw();
		vertexbuffer.begin(1, VertexFormats.POSITION);
		vertexbuffer.vertex(box.minX, box.minY, box.minZ).next();
		vertexbuffer.vertex(box.minX, box.maxY, box.minZ).next();
		vertexbuffer.vertex(box.maxX, box.minY, box.minZ).next();
		vertexbuffer.vertex(box.maxX, box.maxY, box.minZ).next();
		vertexbuffer.vertex(box.maxX, box.minY, box.maxZ).next();
		vertexbuffer.vertex(box.maxX, box.maxY, box.maxZ).next();
		vertexbuffer.vertex(box.minX, box.minY, box.maxZ).next();
		vertexbuffer.vertex(box.minX, box.maxY, box.maxZ).next();
		tessellator.draw();
	}

}
