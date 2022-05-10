package me.deftware.client.framework.wrappers.world;

import me.deftware.mixin.imp.IMixinMinecraft;
import me.deftware.mixin.imp.IMixinTimer;
import net.minecraft.client.Minecraft;

public class ITimer {

	public static double getMinecraftTimerSpeed() {
		IMixinTimer timer = (IMixinTimer) ((IMixinMinecraft) Minecraft.getInstance()).getTimer();
		return timer.getTimerSpeed();
	}

	public static void setMinecraftTimerSpeed(float speed) {
		IMixinTimer timer = (IMixinTimer) ((IMixinMinecraft) Minecraft.getInstance()).getTimer();
		timer.setTimerSpeed(speed);
	}


}
