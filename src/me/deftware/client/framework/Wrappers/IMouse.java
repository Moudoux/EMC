package me.deftware.client.framework.Wrappers;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;

public class IMouse {

	public static boolean hasWheel() {
		return Mouse.hasWheel();
	}

	public static int getDWheel() {
		return Mouse.getDWheel();
	}

	public static void rightClick() {
		Minecraft.getMinecraft().rightClickMouse();
	}

}
