package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.client.Minecraft;

public class ITimer {

	public static double getMinecraftTimerSpeed() {
		return Minecraft.getMinecraft().timer.timerSpeed;
	}
	
	public static void setMinecraftTimerSpeed(float speed) {
		Minecraft.getMinecraft().timer.timerSpeed = speed;
	}


}
