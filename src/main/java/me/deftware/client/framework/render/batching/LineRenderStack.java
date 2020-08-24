package me.deftware.client.framework.render.batching;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.helper.RenderHelper;
import me.deftware.client.framework.math.position.BlockPosition;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

/**
 * @author Deftware
 */
public class LineRenderStack extends RenderStack<LineRenderStack> {

	private Vec3d eyes;

	@Override
	public LineRenderStack begin() {
		return begin(GL11.GL_LINES);
	}

	@Override
	public LineRenderStack setupMatrix() {
		super.setupMatrix();
		eyes = new Vec3d(0.0D, 0.0D, 1.0D)
				.rotateX(-(float) Math.toRadians(RenderHelper.getRotationPitch()))
				.rotateY(-(float) Math.toRadians(RenderHelper.getRotationYaw()));
		return this;
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
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x2, y2);
		return this;
	}

	public LineRenderStack lineToBlockPosition(BlockPosition pos) {
		return drawLine(
				pos.getX() - RenderHelper.getRenderPosX(),
				pos.getY() + 1f / 2.0F - RenderHelper.getRenderPosY(),
				pos.getZ() - RenderHelper.getRenderPosZ()
		);
	}

	public LineRenderStack lineToEntity(TileEntity entity) {
		return lineToBlockPosition(entity.getBlockPosition());
	}

	public LineRenderStack lineToEntity(Entity entity) {
		return drawLine(
				entity.getBlockPosition().getX() - RenderHelper.getRenderPosX(),
				entity.getBlockPosition().getY() + entity.getHeight() / 2.0F - RenderHelper.getRenderPosY(),
				entity.getBlockPosition().getZ() - RenderHelper.getRenderPosZ()
		);
	}

	public LineRenderStack drawLine(double x, double y, double z) {
		GL11.glVertex3d(eyes.x, eyes.y, eyes.z);
		GL11.glVertex3d(x, y, z);
		return this;
	}

}
