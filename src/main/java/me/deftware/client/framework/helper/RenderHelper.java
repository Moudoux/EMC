package me.deftware.client.framework.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.mixin.imp.IMixinRenderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.AoMode;

/**
 * @author Deftware
 */
public class RenderHelper {

	private static AoMode aoMode = null;

	public static void triggerGuiRenderer() {
		RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
	}

	public static void reloadRenderers() {
		if (aoMode == null) aoMode = MinecraftClient.getInstance().options.ao;
		if (SettingsMap.isOverrideMode()) {
			aoMode = MinecraftClient.getInstance().options.ao;
			MinecraftClient.getInstance().options.ao = AoMode.OFF;
		} else {
			MinecraftClient.getInstance().options.ao = aoMode;
		}
		MinecraftClient.getInstance().worldRenderer.reload();
	}

	public static IMixinRenderManager getRenderManager() {
		return (IMixinRenderManager) MinecraftClient.getInstance().getEntityRenderDispatcher();
	}

	public static double getRenderPosX() {
		return getRenderManager().getRenderPosX();
	}

	public static double getRenderPosY() {
		return getRenderManager().getRenderPosY();
	}

	public static double getRenderPosZ() {
		return getRenderManager().getRenderPosZ();
	}

	public static float getRotationYaw() {
		return getRenderManager().getRotationYaw();
	}

	public static float getRotationPitch() {
		return getRenderManager().getRotationPitch();
	}
}
