package me.deftware.client.framework.Utils.Render;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.deftware.client.framework.Wrappers.Entity.IItemEntity;
import me.deftware.client.framework.Wrappers.Entity.IMob;
import me.deftware.client.framework.Wrappers.Entity.IPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderUtils {

	public static void glColor(Color color) {
		GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F,
				color.getAlpha() / 255.0F);
	}

	public static int floor_double(double value) {
		int i = (int) value;
		return value < i ? i - 1 : i;
	}

	public static void enableGL3D(float lineWidth) {
		GL11.glDisable(3008);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glEnable(2884);
		Minecraft.getMinecraft().entityRenderer.disableLightmap();
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glLineWidth(lineWidth);
	}

	public static void drawFilledCircle(int xx, int yy, float radius, int col) {
		float f = (col >> 24 & 0xFF) / 255.0F;
		float f2 = (col >> 16 & 0xFF) / 255.0F;
		float f3 = (col >> 8 & 0xFF) / 255.0F;
		float f4 = (col & 0xFF) / 255.0F;
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glBegin(6);
		for (int i = 0; i < 50; i++) {
			float x = (float) (radius * Math.sin(i * 0.12566370614359174D));
			float y = (float) (radius * Math.cos(i * 0.12566370614359174D));
			GL11.glColor4f(f2, f3, f4, f);
			GL11.glVertex2f(xx + x, yy + y);
		}
		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	public static void blockEspBox(BlockPos blockPos, double red, double green, double blue) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(red, green, blue, 0.15F);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 0F, 0F, 0F, 0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		tessellator.draw();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		tessellator.draw();
		vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		tessellator.draw();
	}

	public static void drawCircle(int xx, int yy, int radius, int col) {
		float f = (col >> 24 & 0xFF) / 255.0F;
		float f2 = (col >> 16 & 0xFF) / 255.0F;
		float f3 = (col >> 8 & 0xFF) / 255.0F;
		float f4 = (col & 0xFF) / 255.0F;
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glShadeModel(7425);
		GL11.glBegin(2);
		for (int i = 0; i < 70; i++) {
			float x = (float) (radius * Math.cos(i * 0.08975979010256552D));
			float y = (float) (radius * Math.sin(i * 0.08975979010256552D));
			GL11.glColor4f(f2, f3, f4, f);
			GL11.glVertex2f(xx + x, yy + y);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glPopMatrix();
	}

	public static void drawRect(float g, float h, float i, float j, Color c) {
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		glColor(c);
		GL11.glBegin(7);
		GL11.glVertex2d(i, h);
		GL11.glVertex2d(g, h);
		GL11.glVertex2d(g, j);
		GL11.glVertex2d(i, j);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public static void drawRect(float g, float h, float i, float j, int col1) {
		float f = (col1 >> 24 & 0xFF) / 255.0F;
		float f2 = (col1 >> 16 & 0xFF) / 255.0F;
		float f3 = (col1 >> 8 & 0xFF) / 255.0F;
		float f4 = (col1 & 0xFF) / 255.0F;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glColor4f(f2, f3, f4, f);
		GL11.glBegin(7);
		GL11.glVertex2d(i, h);
		GL11.glVertex2d(g, h);
		GL11.glVertex2d(g, j);
		GL11.glVertex2d(i, j);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
		drawRect(x, y, x2, y2, col2);
		float f = (col1 >> 24 & 0xFF) / 255.0F;
		float f2 = (col1 >> 16 & 0xFF) / 255.0F;
		float f3 = (col1 >> 8 & 0xFF) / 255.0F;
		float f4 = (col1 & 0xFF) / 255.0F;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glColor4f(f2, f3, f4, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(1);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, Color c, Color c2) {
		drawRect(x, y, x2, y2, c2);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		glColor(c);
		GL11.glLineWidth(l1);
		GL11.glBegin(1);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public static void box(double x, double y, double z, double x2, double y2, double z2, float red, float green,
			float blue, float alpha) {
		x -= Minecraft.getMinecraft().getRenderManager().renderPosX;
		y -= Minecraft.getMinecraft().getRenderManager().renderPosY;
		z -= Minecraft.getMinecraft().getRenderManager().renderPosZ;
		x2 -= Minecraft.getMinecraft().getRenderManager().renderPosX;
		y2 -= Minecraft.getMinecraft().getRenderManager().renderPosY;
		z2 -= Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawColorBox(new AxisAlignedBB(x, y, z, x2, y2, z2), red, green, blue, alpha);
		GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GlStateManager.resetColor();
	}

	public static void frame(double x, double y, double z, double x2, double y2, double z2, Color color) {
		x -= Minecraft.getMinecraft().getRenderManager().renderPosX;
		y -= Minecraft.getMinecraft().getRenderManager().renderPosY;
		z -= Minecraft.getMinecraft().getRenderManager().renderPosZ;
		x2 -= Minecraft.getMinecraft().getRenderManager().renderPosX;
		y2 -= Minecraft.getMinecraft().getRenderManager().renderPosY;
		z2 -= Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		glColor(color);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
	}

	public static void blockESPBox(BlockPos blockPos) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4d(0.0D, 1.0D, 0.0D, 0.26000000596046448D);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 0.0F, 1.0F, 0.0F, 0.15F);
		GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
	}

	public static void blockESPBox(BlockPos blockPos, float red, float green, float blue) {
		RenderUtils.fixDarkLight();
		GlStateManager.resetColor();
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, 0.15F);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 0.0F, 1.0F, 0.0F, 0.15F);
		GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GlStateManager.resetColor();
	}

	public static void fixDarkLight() {
		GlStateManager.enableLighting();
		RenderHelper.disableStandardItemLighting();
	}

	public static void blockESPBox(BlockPos blockPos, Color c) {
		RenderUtils.fixDarkLight();
		GlStateManager.resetColor();
		float red = c.getRed(), green = c.getGreen(), blue = c.getBlue();
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, 0.15F);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 0.0F, 1.0F, 0.0F, 0.15F);
		GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GlStateManager.resetColor();
	}

	public static void framelessBlockESP(BlockPos blockPos, float red, float green, float blue) {
		RenderUtils.fixDarkLight();
		GlStateManager.resetColor();
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, 0.15F);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), red, green, blue, 0.15F);
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GlStateManager.resetColor();
	}

	public static void emptyBlockESPBox(BlockPos blockPos) {
		RenderUtils.fixDarkLight();
		GlStateManager.resetColor();
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
	}

	public static int enemy = 0;
	public static int friend = 1;
	public static int other = 2;
	public static int target = 3;
	public static int team = 4;

	public static void entityESPBox(Entity entity, int mode) {
		RenderUtils.fixDarkLight();
		GlStateManager.resetColor();
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		if (mode == 0) {
			GL11.glColor4d(1.0F - Minecraft.getMinecraft().player.getDistanceToEntity(entity) / 40.0F,
					Minecraft.getMinecraft().player.getDistanceToEntity(entity) / 40.0F, 0.0D, 0.5D);
		} else if (mode == 1) {
			GL11.glColor4d(0.0D, 0.0D, 1.0D, 0.5D);
		} else if (mode == 2) {
			GL11.glColor4d(1.0D, 1.0D, 0.0D, 0.5D);
		} else if (mode == 3) {
			GL11.glColor4d(1.0D, 0.0D, 0.0D, 0.5D);
		} else if (mode == 4) {
			GL11.glColor4d(0.0D, 1.0D, 0.0D, 0.5D);
		}
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		drawSelectionBoundingBox(new AxisAlignedBB(
				entity.boundingBox.minX - 0.05D - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.minY - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.minZ - 0.05D - entity.posZ + (entity.posZ - renderManager.renderPosZ),
				entity.boundingBox.maxX + 0.05D - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.maxY + 0.1D - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.maxZ + 0.05D - entity.posZ + (entity.posZ - renderManager.renderPosZ)));
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
	}

	public static void nukerBox(BlockPos blockPos, float damage) {
		RenderUtils.fixDarkLight();
		GlStateManager.resetColor();
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4f(damage, 1.0F - damage, 0.0F, 0.15F);
		drawColorBox(
				new AxisAlignedBB(x + 0.5D - damage / 2.0F, y + 0.5D - damage / 2.0F, z + 0.5D - damage / 2.0F,
						x + 0.5D + damage / 2.0F, y + 0.5D + damage / 2.0F, z + 0.5D + damage / 2.0F),
				damage, 1.0F - damage, 0.0F, 0.15F);
		GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D);
		drawSelectionBoundingBox(
				new AxisAlignedBB(x + 0.5D - damage / 2.0F, y + 0.5D - damage / 2.0F, z + 0.5D - damage / 2.0F,
						x + 0.5D + damage / 2.0F, y + 0.5D + damage / 2.0F, z + 0.5D + damage / 2.0F));
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
	}

	public static void searchBox(BlockPos blockPos) {
		RenderUtils.fixDarkLight();
		GlStateManager.resetColor();
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(1.0F);
		float sinus = 1.0F - MathHelper
				.abs(MathHelper.sin(Minecraft.getSystemTime() % 10000L / 10000.0F * 3.1415927F * 4.0F) * 1.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4f(1.0F - sinus, sinus, 0.0F, 0.15F);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 1.0F - sinus, sinus, 0.0F, 0.15F);
		GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
	}

	public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
		Tessellator ts = Tessellator.getInstance();
		BufferBuilder vb = ts.getBuffer();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
	}

	public static void tracerLine(IItemEntity entity, int mode) {
		tracerLine(entity.getItem(), mode);
	}

	public static void tracerLine(IPlayer entity, int mode) {
		tracerLine(entity.getPlayer(), mode);
	}

	public static void tracerLine(IMob entity, int mode) {
		tracerLine(entity.getMob(), mode);
	}

	public static void tracerLine(Entity entity, int mode) {
		RenderUtils.fixDarkLight();
		GlStateManager.resetColor();
		double x = entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = entity.posY + entity.height / 2.0F - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		if (mode == 0) {
			GL11.glColor4d(1.0F - Minecraft.getMinecraft().player.getDistanceToEntity(entity) / 40.0F,
					Minecraft.getMinecraft().player.getDistanceToEntity(entity) / 40.0F, 0.0D, 0.5D);
		} else if (mode == 1) {
			GL11.glColor4d(0.0D, 0.0D, 1.0D, 0.5D);
		} else if (mode == 2) {
			GL11.glColor4d(1.0D, 1.0D, 0.0D, 0.5D);
		} else if (mode == 3) {
			GL11.glColor4d(1.0D, 0.0D, 0.0D, 0.5D);
		} else if (mode == 4) {
			GL11.glColor4d(0.0D, 1.0D, 0.0D, 0.5D);
		}
		Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D)
				.rotatePitch(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationPitch))
				.rotateYaw(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationYaw));

		GL11.glBegin(1);

		GL11.glVertex3d(eyes.xCoord, Minecraft.getMinecraft().player.getEyeHeight() + eyes.yCoord, eyes.zCoord);
		GL11.glVertex3d(x, y, z);

		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
	}

	public static void tracerLine(Entity entity, Color color) {
		RenderUtils.fixDarkLight();
		GlStateManager.resetColor();

		double x = entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = entity.posY + entity.height / 2.0F - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ;

		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);

		glColor(color);

		Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D)
				.rotatePitch(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationPitch))
				.rotateYaw(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationYaw));

		GL11.glBegin(1);

		GL11.glVertex3d(eyes.xCoord, Minecraft.getMinecraft().player.getEyeHeight() + eyes.yCoord, eyes.zCoord);
		GL11.glVertex3d(x, y, z);

		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
	}

	public static void tracerLine(Entity entity, Color color, float alpha) {
		RenderUtils.fixDarkLight();
		GlStateManager.resetColor();
		double x = entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX + 0.5f;
		double y = entity.posY + entity.height / 2.0F - Minecraft.getMinecraft().getRenderManager().renderPosY - 0.5f;
		double z = entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ + 0.5f;

		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);

		GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), alpha);

		Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D)
				.rotatePitch(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationPitch))
				.rotateYaw(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationYaw));

		GL11.glBegin(1);

		GL11.glVertex3d(eyes.xCoord, Minecraft.getMinecraft().player.getEyeHeight() + eyes.yCoord, eyes.zCoord);
		GL11.glVertex3d(x, y, z);

		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);

		GlStateManager.resetColor();
	}

	public static void enableGL2D() {
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glDepthMask(true);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
	}

	public static void enableGL3D() {
		GL11.glDisable(3008);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glEnable(2884);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4353);
		GL11.glDisable(2896);
	}

	public static void disableGL3D() {
		GL11.glEnable(2896);
		GL11.glDisable(2848);
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDisable(3042);
		GL11.glEnable(3008);
		GL11.glDepthMask(true);
		GL11.glCullFace(1029);
	}

	public static void disableGL2D() {
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glEnable(2929);
		GL11.glDisable(2848);
		GL11.glHint(3154, 4352);
		GL11.glHint(3155, 4352);
	}

	public static void glColor(int hex) {
		float alpha = (hex >> 24 & 0xFF) / 255.0F;
		float red = (hex >> 16 & 0xFF) / 255.0F;
		float green = (hex >> 8 & 0xFF) / 255.0F;
		float blue = (hex & 0xFF) / 255.0F;
		GL11.glColor4f(red, green, blue, alpha);
	}

	public static int reAlpha(int color, float alpha) {
		Color c = new Color(color);
		float r = ((float) 1 / 255) * c.getRed();
		float g = ((float) 1 / 255) * c.getGreen();
		float b = ((float) 1 / 255) * c.getBlue();
		return new Color(r, g, b, alpha).getRGB();
	}

	public static void drawBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int inside,
			int border) {
		enableGL2D();
		drawRect(x, y, x1, y1, inside);
		glColor(border);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(lineWidth);
		GL11.glBegin(3);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, y1);
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x1, y);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		disableGL2D();
	}

	public static void tracerLine(int x, int y, int z, Color color) {
		x = (int) (x + (0.5D - Minecraft.getMinecraft().getRenderManager().renderPosX));
		y = (int) (y + (0.5D - Minecraft.getMinecraft().getRenderManager().renderPosY));
		z = (int) (z + (0.5D - Minecraft.getMinecraft().getRenderManager().renderPosZ));
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		glColor(color);
		GL11.glBegin(1);

		GL11.glVertex3d(0.0D, Minecraft.getMinecraft().player.getEyeHeight(), 0.0D);
		GL11.glVertex3d(x, y, z);

		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
	}
}
