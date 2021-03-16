package me.deftware.client.framework.render.batching;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.minecraft.Minecraft;
import net.minecraft.client.render.*;
import net.minecraft.util.math.Box;
import org.lwjgl.opengl.GL11;

/**
 * @author Deftware
 */
public class CubeRenderStack extends RenderStack<CubeRenderStack> {

	private boolean lines = false;

	@Override
	public CubeRenderStack begin() {
		return begin(GL11.GL_QUADS);
	}

	@Override
	protected VertexFormat getFormat() {
		if (lines)
			// POSITION_COLOR_NORMAL_PADDING
			return VertexFormats.field_29337;
		return super.getFormat();
	}

	@Override
	protected void setShader() {
		if (lines) {
			// POSITION_COLOR_NORMAL_PADDING (rendertype_lines)
			RenderSystem.setShader(GameRenderer::method_34535);
			return;
		}
		super.setShader();
	}

	@Override
	protected VertexConsumer vertex(double x, double y, double z) {
		VertexConsumer vertex = super.vertex(x, y, z);
		if (lines)
			vertex.normal(0, 0, 0);
		return vertex;
	}

	public CubeRenderStack begin(boolean lines) {
		return begin(
				(this.lines = lines) ? GL11.GL_LINE_STRIP : GL11.GL_QUADS
		);
	}

	public CubeRenderStack draw(BoundingBox box) {
		if (box == null)
			return this;
		Box minecraftBox = box.getOffsetMinecraftBox(-Minecraft.getCamera().getRenderPosX(), -Minecraft.getCamera().getRenderPosY(), -Minecraft.getCamera().getRenderPosZ());
		if (lines)
			drawSelectionBoundingBox(minecraftBox);
		else
			drawColorBox(minecraftBox);
		return this;
	}

	private void drawColorBox(Box box) {
		vertex(box.minX, box.minY, box.minZ).next();
		vertex(box.minX, box.maxY, box.minZ).next();
		vertex(box.maxX, box.minY, box.minZ).next();
		vertex(box.maxX, box.maxY, box.minZ).next();
		vertex(box.maxX, box.minY, box.maxZ).next();
		vertex(box.maxX, box.maxY, box.maxZ).next();
		vertex(box.minX, box.minY, box.maxZ).next();
		vertex(box.minX, box.maxY, box.maxZ).next();

		vertex(box.maxX, box.maxY, box.minZ).next();
		vertex(box.maxX, box.minY, box.minZ).next();
		vertex(box.minX, box.maxY, box.minZ).next();
		vertex(box.minX, box.minY, box.minZ).next();
		vertex(box.minX, box.maxY, box.maxZ).next();
		vertex(box.minX, box.minY, box.maxZ).next();
		vertex(box.maxX, box.maxY, box.maxZ).next();
		vertex(box.maxX, box.minY, box.maxZ).next();

		vertex(box.minX, box.maxY, box.minZ).next();
		vertex(box.maxX, box.maxY, box.minZ).next();
		vertex(box.maxX, box.maxY, box.maxZ).next();
		vertex(box.minX, box.maxY, box.maxZ).next();
		vertex(box.minX, box.maxY, box.minZ).next();
		vertex(box.minX, box.maxY, box.maxZ).next();
		vertex(box.maxX, box.maxY, box.maxZ).next();
		vertex(box.maxX, box.maxY, box.minZ).next();

		vertex(box.minX, box.minY, box.minZ).next();
		vertex(box.maxX, box.minY, box.minZ).next();
		vertex(box.maxX, box.minY, box.maxZ).next();
		vertex(box.minX, box.minY, box.maxZ).next();
		vertex(box.minX, box.minY, box.minZ).next();
		vertex(box.minX, box.minY, box.maxZ).next();
		vertex(box.maxX, box.minY, box.maxZ).next();
		vertex(box.maxX, box.minY, box.minZ).next();

		vertex(box.minX, box.minY, box.minZ).next();
		vertex(box.minX, box.maxY, box.minZ).next();
		vertex(box.minX, box.minY, box.maxZ).next();
		vertex(box.minX, box.maxY, box.maxZ).next();
		vertex(box.maxX, box.minY, box.maxZ).next();
		vertex(box.maxX, box.maxY, box.maxZ).next();
		vertex(box.maxX, box.minY, box.minZ).next();
		vertex(box.maxX, box.maxY, box.minZ).next();

		vertex(box.minX, box.maxY, box.maxZ).next();
		vertex(box.minX, box.minY, box.maxZ).next();
		vertex(box.minX, box.maxY, box.minZ).next();
		vertex(box.minX, box.minY, box.minZ).next();
		vertex(box.maxX, box.maxY, box.minZ).next();
		vertex(box.maxX, box.minY, box.minZ).next();
		vertex(box.maxX, box.maxY, box.maxZ).next();
		vertex(box.maxX, box.minY, box.maxZ).next();
	}

	private void drawSelectionBoundingBox(Box box) {
		vertex(box.minX, box.minY, box.minZ).next();
		vertex(box.maxX, box.minY, box.minZ).next();
		vertex(box.maxX, box.minY, box.maxZ).next();
		vertex(box.minX, box.minY, box.maxZ).next();
		vertex(box.minX, box.minY, box.minZ).next();

		vertex(box.minX, box.maxY, box.minZ).next();
		vertex(box.maxX, box.maxY, box.minZ).next();
		vertex(box.maxX, box.maxY, box.maxZ).next();
		vertex(box.minX, box.maxY, box.maxZ).next();
		vertex(box.minX, box.maxY, box.minZ).next();

		builder.end();
		BufferRenderer.draw(builder);

		builder.begin(VertexFormat.DrawMode.LINES, getFormat());

		vertex(box.minX, box.minY, box.minZ).next();
		vertex(box.minX, box.maxY, box.minZ).next();
		vertex(box.maxX, box.minY, box.minZ).next();
		vertex(box.maxX, box.maxY, box.minZ).next();
		vertex(box.maxX, box.minY, box.maxZ).next();
		vertex(box.maxX, box.maxY, box.maxZ).next();
		vertex(box.minX, box.minY, box.maxZ).next();
		vertex(box.minX, box.maxY, box.maxZ).next();
	}

}
