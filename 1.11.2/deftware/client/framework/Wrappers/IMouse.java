package me.deftware.client.framework.Wrappers;

import org.lwjgl.input.Mouse;

public class IMouse {

	public static boolean hasWheel() {
		return Mouse.hasWheel();
	}

	public static int getDWheel() {
		return Mouse.getDWheel();
	}

}
