package me.deftware.client.framework.Wrappers;

import net.minecraft.util.math.MathHelper;

public class IMath {

	public static double wrapDegrees(double value) {
		return MathHelper.wrapDegrees(value);
	}
	
	public static float sin(float value) {
		return MathHelper.sin(value);
	}

	public static float cos(float value) {
		return MathHelper.cos(value);
	}
	
}
