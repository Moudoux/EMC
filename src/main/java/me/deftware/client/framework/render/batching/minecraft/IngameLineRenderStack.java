package me.deftware.client.framework.render.batching.minecraft;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.helper.RenderHelper;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.render.batching.RenderStack;
import me.deftware.client.framework.render.camera.GameCamera;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

/**
 * 3d lines in-game
 *
 * @author Deftware
 */
public class IngameLineRenderStack extends RenderStack<IngameLineRenderStack> {

	private Vec3d eyes;

	@Override
	public IngameLineRenderStack begin() {
		return begin(GL11.GL_LINES);
	}

	@Override
	public IngameLineRenderStack setupMatrix(int matrixWidth, int matrixHeight) {
		RenderSystem.clearCurrentColor();
		// Setup gl
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(lineWidth);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		// Eyes vector
		eyes = new Vec3d(0.0D, 0.0D, 1.0D)
				.rotateX(-(float) Math.toRadians(Objects.requireNonNull(GameCamera.isActive() ? GameCamera.fakePlayer : MinecraftClient.getInstance().player).pitch))
				.rotateY(-(float) Math.toRadians(Objects.requireNonNull(GameCamera.isActive() ? GameCamera.fakePlayer : MinecraftClient.getInstance().player).yaw));
		return this;
	}

	public IngameLineRenderStack lineToBlockPosition(BlockPosition pos) {
		return drawLine(
				pos.getX() - RenderHelper.getRenderPosX(),
				pos.getY() + 1f / 2.0F - RenderHelper.getRenderPosY(),
				pos.getZ() - RenderHelper.getRenderPosZ()
		);
	}

	public IngameLineRenderStack lineToEntity(TileEntity entity) {
		return lineToBlockPosition(entity.getBlockPosition());
	}

	public IngameLineRenderStack lineToEntity(Entity entity) {
		return drawLine(
				entity.getBlockPosition().getX() - RenderHelper.getRenderPosX(),
				entity.getBlockPosition().getY() + entity.getHeight() / 2.0F - RenderHelper.getRenderPosY(),
				entity.getBlockPosition().getZ() - RenderHelper.getRenderPosZ()
		);
	}

	public IngameLineRenderStack drawLine(double x, double y, double z) {
		GL11.glVertex3d(eyes.x, eyes.y, eyes.z);
		GL11.glVertex3d(x, y, z);
		return this;
	}

	@Override
	public void end() {
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

}
