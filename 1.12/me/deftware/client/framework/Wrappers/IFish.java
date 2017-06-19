package me.deftware.client.framework.Wrappers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.projectile.EntityFishHook;

public class IFish {

	private static EntityFishHook getEntity() {
		return Minecraft.getMinecraft().player.fishEntity;
	}

	public static boolean isNull() {
		return getEntity() == null;
	}

	public static int getPosY() {
		return (int) getEntity().posY;
	}

}
