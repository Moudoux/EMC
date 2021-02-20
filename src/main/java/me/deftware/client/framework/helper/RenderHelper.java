package me.deftware.client.framework.helper;

import me.deftware.client.framework.main.bootstrap.Bootstrap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.AoMode;

/**
 * @author Deftware
 */
public class RenderHelper {

	private static AoMode aoMode = null;

	public static void reloadRenderers() {
		if (aoMode == null) aoMode = MinecraftClient.getInstance().options.ao;
		if (Bootstrap.blockProperties.isActive()) {
			aoMode = MinecraftClient.getInstance().options.ao;
			MinecraftClient.getInstance().options.ao = AoMode.OFF;
		} else {
			MinecraftClient.getInstance().options.ao = aoMode;
		}
		MinecraftClient.getInstance().worldRenderer.reload();
	}

}
