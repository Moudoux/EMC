package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

public class ITimer extends Timer {

	public ITimer(float tps) {
		super(tps);
	}
	
	/**
	
	public float getTimerSpeed() {
		return this.timerSpeed;
	}
	
	public void setTimerSpeed(float speed) {
		this.timerSpeed = speed;
	}
	
	public static float getMinecraftTimerSpeed() {
		return Minecraft.getMinecraft().timer.timerSpeed;
	}
	
	public static void setMinecraftTimerSpeed(float speed) {
		Minecraft.getMinecraft().timer.timerSpeed = speed;
	}
	
	**/

}
