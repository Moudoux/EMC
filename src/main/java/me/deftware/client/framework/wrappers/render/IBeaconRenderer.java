package me.deftware.client.framework.wrappers.render;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.utils.render.TexUtil;
import me.deftware.client.framework.wrappers.IResourceLocation;
import me.deftware.client.framework.wrappers.entity.IEntityPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.block.entity.EndGatewayBlockEntityRenderer;
import net.minecraft.util.DyeColor;

public class IBeaconRenderer {

    /*
    public static final IResourceLocation TEXTURE_BEACON_BEAM = new IResourceLocation("textures/entity/beacon_beam.png");

    public static void renderBeamAt(double x, double y, double z, float r, float g, float b, int height, float partialTicks) {
        if (height < 1) {
            throw new IllegalArgumentException("Height cannot be less than 1");
        }
        RenderSystem.alphaFunc(516, 0.1F);
        TexUtil.bindTexture(TEXTURE_BEACON_BEAM);
        RenderSystem.disableFog();
        x -= (IEntityPlayer.getPrevPosX() + (IEntityPlayer.getPosX() - IEntityPlayer.getPrevPosX()) * (double) partialTicks);
        y -= (IEntityPlayer.getPrevPosY() + (IEntityPlayer.getPosY() - IEntityPlayer.getPrevPosY()) * (double) partialTicks);
        z -= (IEntityPlayer.getPrevPosZ() + (IEntityPlayer.getPosZ() - IEntityPlayer.getPrevPosZ()) * (double) partialTicks);
        BeaconBlockEntityRenderer.renderLightBeam(x, y, z, partialTicks, 1.0, MinecraftClient.getInstance().world.getTimeOfDay(), 0, height, new float[]{r, g, b}, 0.2D, 0.25D);
    }

    public static void renderBeamAt(double x, double y, double z, int height, float partialTicks) {
        float[] white = DyeColor.WHITE.getColorComponents();
        renderBeamAt(x, y, z, white[0], white[1], white[2], height, partialTicks);
    }*/

}
