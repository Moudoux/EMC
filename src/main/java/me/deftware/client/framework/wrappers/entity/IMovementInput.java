package me.deftware.client.framework.wrappers.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;

public class IMovementInput {

	private static MovementInput get() {
		return Minecraft.getMinecraft().player.movementInput;
	}

	public static double getForward() {
		return IMovementInput.get().moveForward;
	}

	public static double getStrafe() {
		return IMovementInput.get().moveStrafe;
	}

}
