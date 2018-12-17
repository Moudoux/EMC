package me.deftware.client.framework.wrappers.render;

import me.deftware.client.framework.utils.TexUtil;
import me.deftware.client.framework.wrappers.IResourceLocation;
import me.deftware.client.framework.wrappers.entity.IEntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.item.EnumDyeColor;

public class IBeaconRenderer {

	public static final IResourceLocation TEXTURE_BEACON_BEAM = new IResourceLocation("textures/entity/beacon_beam.png");

	public static void renderBeamAt(double x, double y, double z, float r, float g, float b, int height, float partialTicks) {
		if (height < 1) {
			throw new IllegalArgumentException("Height cannot be less than 1");
		}
		GlStateManager.alphaFunc(516, 0.1F);
		TexUtil.bindTexture(TEXTURE_BEACON_BEAM);
		GlStateManager.disableFog();
		x -= (IEntityPlayer.getPrevPosX() + (IEntityPlayer.getPosX() - IEntityPlayer.getPrevPosX()) * (double) partialTicks);
		y -= (IEntityPlayer.getPrevPosY() + (IEntityPlayer.getPosY() - IEntityPlayer.getPrevPosY()) * (double) partialTicks);
		z -= (IEntityPlayer.getPrevPosZ() + (IEntityPlayer.getPosZ() - IEntityPlayer.getPrevPosZ()) * (double) partialTicks);
		TileEntityBeaconRenderer.renderBeamSegment(x, y, z, partialTicks, 1.0, Minecraft.getInstance().world.getDayTime(), 0, height, new float[]{r, g, b}, 0.2D, 0.25D);
	}

	public static void renderBeamAt(double x, double y, double z, int height, float partialTicks) {
		float[] white = EnumDyeColor.WHITE.getColorComponentValues();
		renderBeamAt(x, y, z, white[0], white[1], white[2], height, partialTicks);
	}

}
