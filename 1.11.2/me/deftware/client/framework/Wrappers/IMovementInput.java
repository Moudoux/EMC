package me.deftware.client.framework.Wrappers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;

public class IMovementInput {

	private static MovementInput get() {
		return Minecraft.getMinecraft().player.movementInput;
	}

	public static double getForward() {
		return get().moveForward;
	}

	public static double getStrafe() {
		return get().moveStrafe;
	}

}
