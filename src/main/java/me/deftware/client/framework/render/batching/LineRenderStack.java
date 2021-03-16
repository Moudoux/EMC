package me.deftware.client.framework.render.batching;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.minecraft.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

/**
 * @author Deftware
 */
public class LineRenderStack extends RenderStack<LineRenderStack> {

	private Vec3d eyes;

	@Override
	public LineRenderStack begin() {
		eyes = new Vec3d(0.0D, 0.0D, 1.0D)
				.rotateX(-(float) Math.toRadians(Minecraft.getCamera().getRotationPitch()))
				.rotateY(-(float) Math.toRadians(Minecraft.getCamera().getRotationYaw()));
		return begin(GL11.GL_LINES);
	}

	@Override
	protected VertexFormat getFormat() {
		// POSITION_COLOR_NORMAL_PADDING
		return VertexFormats.field_29337;
	}

	@Override
	protected void setShader() {
		// POSITION_COLOR_NORMAL_PADDING (rendertype_lines)
		RenderSystem.setShader(GameRenderer::method_34535);
	}

	@Override
	protected VertexConsumer vertex(double x, double y, double z) {
		return super.vertex(x, y, z).normal(1, 1, 1);
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
		vertex(x1, y1, 0).next();
		vertex(x2, y2, 0).next();
		return this;
	}

	public void vertex(double x, double y) {
		vertex(x, y, 0).next();
	}

	public LineRenderStack lineToBlockPosition(BlockPosition pos) {
		return drawLine(
				pos.getX() - Minecraft.getCamera().getRenderPosX(),
				pos.getY() + 1f / 2.0F - Minecraft.getCamera().getRenderPosY(),
				pos.getZ() - Minecraft.getCamera().getRenderPosZ()
		);
	}

	public LineRenderStack lineToEntity(TileEntity entity) {
		return lineToBlockPosition(entity.getBlockPosition());
	}

	public LineRenderStack lineToEntity(Entity entity) {
		return drawLine(
				entity.getBlockPosition().getX() - Minecraft.getCamera().getRenderPosX(),
				entity.getBlockPosition().getY() + entity.getHeight() / 2.0F - Minecraft.getCamera().getRenderPosY(),
				entity.getBlockPosition().getZ() - Minecraft.getCamera().getRenderPosZ()
		);
	}

	public LineRenderStack drawPoint(double x, double y, double z) {
		vertex(x, y, z).next();
		return this;
	}

	public LineRenderStack drawLine(double x, double y, double z) {
		return drawPoint(eyes.x, eyes.y, eyes.z).drawPoint(x, y, z);
	}

}
