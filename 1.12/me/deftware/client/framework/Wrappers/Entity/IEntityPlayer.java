package me.deftware.client.framework.Wrappers.Entity;

import net.minecraft.client.Minecraft;

/**
 * EntityPlayerSP wrapper
 * 
 * @author deftware
 *
 */
public class IEntityPlayer {

	public static boolean isRowingBoat() {
		if (isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isRowingBoat();
	}

	public static boolean isInLiquid() {
		if (isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isInWater() || Minecraft.getMinecraft().player.isInLava();
	}

	public static void setFlying(boolean state) {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.capabilities.isFlying = state;
	}

	public static boolean isFlying() {
		if (isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.capabilities.isFlying;
	}

	public static void setFlySpeed(float speed) {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.capabilities.setFlySpeed(speed);
	}

	public static float getFlySpeed() {
		if (isNull()) {
			return 0F;
		}
		return Minecraft.getMinecraft().player.capabilities.getFlySpeed();
	}

	public static void setWalkSpeed(float speed) {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.capabilities.setPlayerWalkSpeed(speed);
	}

	public static float getWalkSpeed() {
		if (isNull()) {
			return 0F;
		}
		return Minecraft.getMinecraft().player.capabilities.getWalkSpeed();
	}

	/**
	 * Is the Minecraft game even loaded ?
	 * 
	 * @return
	 */
	public static boolean isNull() {
		if (Minecraft.getMinecraft().player == null) {
			return true;
		}
		return false;
	}

}
