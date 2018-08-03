package me.deftware.client.framework.wrappers;


import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class IMouse {

	public static ArrayList<MouseScrollCallback> scrollCallbacks = new ArrayList<>();

	/**
	 * @see GLFW#GLFW_MOUSE_BUTTON_LEFT
	 * @see GLFW#GLFW_MOUSE_BUTTON_RIGHT
	 * @see GLFW#GLFW_MOUSE_BUTTON_MIDDLE
	 */
	public static void clickMouse(int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			((IMixinMinecraft) Minecraft.getMinecraft()).doClickMouse();
		} else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			((IMixinMinecraft) Minecraft.getMinecraft()).doRightClickMouse();
		} else	if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
			((IMixinMinecraft) Minecraft.getMinecraft()).doMiddleClickMouse();
		}
	}

	public static void registerScrollHook(MouseScrollCallback cb) {
		scrollCallbacks.add(cb);
	}

	public static void onScroll(double x, double y) {
		scrollCallbacks.forEach((cb) -> cb.onScroll(x, y));
	}

	@FunctionalInterface
	public interface MouseScrollCallback {

		void onScroll(double xoffset, double yoffset);

	}

}
