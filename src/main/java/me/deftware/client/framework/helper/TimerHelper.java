package me.deftware.client.framework.helper;

import me.deftware.mixin.imp.IMixinMinecraft;
import me.deftware.mixin.imp.IMixinTimer;
import net.minecraft.client.MinecraftClient;

/**
 * @author Deftware
 */
public class TimerHelper {

	public static double getMinecraftTimerSpeed() {
		IMixinTimer timer = (IMixinTimer) ((IMixinMinecraft) MinecraftClient.getInstance()).getTimer();
		return timer.getTimerSpeed();
	}

	public static void setMinecraftTimerSpeed(float speed) {
		IMixinTimer timer = (IMixinTimer) ((IMixinMinecraft) MinecraftClient.getInstance()).getTimer();
		timer.setTimerSpeed(speed);
	}

}
