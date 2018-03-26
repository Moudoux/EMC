package me.deftware.client.framework.wrappers;

import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public class IMouse {

	public static boolean hasWheel() {
		return Mouse.hasWheel();
	}

	public static int getDWheel() {
		return Mouse.getDWheel();
	}

	public static void rightClick() {
		((IMixinMinecraft) Minecraft.getMinecraft()).doRightClickMouse();
	}

}
