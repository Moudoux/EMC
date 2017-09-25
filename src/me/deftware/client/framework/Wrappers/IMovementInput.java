package me.deftware.client.framework.Wrappers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;

public class IMovementInput {

	private static MovementInput get() {
		return Minecraft.getMinecraft().player.movementInput;
	}

	public static double getForward() {
		return get().field_192832_b;
	}

	public static double getStrafe() {
		return get().moveStrafe;
	}

}
