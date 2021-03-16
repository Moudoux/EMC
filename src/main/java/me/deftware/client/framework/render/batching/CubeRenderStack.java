package me.deftware.client.framework.render.batching;

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

	public CubeRenderStack() {
		customMatrix = false;
	}

	@Override
	public CubeRenderStack begin() {
		return begin(GL11.GL_QUADS);
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
		vertex(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();

		vertex(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).next();

		vertex(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).next();

		vertex(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).next();

		vertex(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).next();

		vertex(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
	}

	private void drawSelectionBoundingBox(Box box) {
		vertex(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).next();

		vertex(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).next();

		builder.end();
		BufferRenderer.draw(builder);
		builder.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);

		vertex(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).next();
		vertex(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).next();
	}

}
