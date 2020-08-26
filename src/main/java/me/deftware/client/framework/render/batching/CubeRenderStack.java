package me.deftware.client.framework.render.batching;

import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.minecraft.Minecraft;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.Box;

/**
 * @author Deftware
 */
public class CubeRenderStack extends RenderStack<CubeRenderStack> {

	public CubeRenderStack() {
		customMatrix = false;
	}

	@Override
	public CubeRenderStack begin() {
		return this; /* Not used in this stack */
	}

	public CubeRenderStack ESPBox(BoundingBox box) {
		if (box == null) return this;
		drawColorBox(box.getOffsetMinecraftBox(-Minecraft.getCamera().getRenderPosX(), -Minecraft.getCamera().getRenderPosY(), -Minecraft.getCamera().getRenderPosZ()));
		return this;
	}

	public CubeRenderStack emptyESPBox(BoundingBox box) {
		drawSelectionBoundingBox(box.getOffsetMinecraftBox(-Minecraft.getCamera().getRenderPosX(), -Minecraft.getCamera().getRenderPosY(), -Minecraft.getCamera().getRenderPosZ()));
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
