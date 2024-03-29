package me.deftware.client.framework.input;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author Deftware
 */
public class Keyboard {

	public static HashMap<Integer, String> normalKeys = new HashMap<>(),
								functionKeys = new HashMap<>(),
								mouseButtons = new HashMap<>();

	public static void populateCodePoints() {
		for (Field field : GLFW.class.getDeclaredFields()) {
			try {
				if (field.getType() == int.class) {
					String name = field.getName();
					int codePoint = field.getInt(null);
					// Normal keys
					if (name.startsWith("GLFW_KEY_")) {
						if (codePoint >= 32 && codePoint <= 162) {
							normalKeys.put(codePoint, name.substring("GLFW_KEY_".length()));
						} else if (codePoint >= 256 && codePoint <= 348) {
							functionKeys.put(codePoint, name.substring("GLFW_KEY_".length()));
						}
					} else if (name.startsWith("GLFW_MOUSE_")) {
						mouseButtons.put(codePoint, name.substring("GLFW_MOUSE_".length()));
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String getKeyName(int glfwCodePoint) {
		int scanCode = GLFW.glfwGetKeyScancode(glfwCodePoint);
		return getKeyName(glfwCodePoint, scanCode);
	}

	public static String getKeyName(int codePoint, int scanCode) {
		String name = GLFW.glfwGetKeyName(codePoint, scanCode);
		if (name == null) {
			return normalKeys.getOrDefault(codePoint, functionKeys.getOrDefault(codePoint, "Unknown"));
		}
		return name;
	}

	public static String getClipboardString() {
		return MinecraftClient.getInstance().keyboard.getClipboard();
	}

	public static void setClipboardString(String copyText) {
		MinecraftClient.getInstance().keyboard.setClipboard(copyText);
	}

	public static boolean isKeyDown(int key) {
		if (key <= 2) return false;
		return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), key);
	}

	public static void openLink(String url) {
		Util.getOperatingSystem().open(url);
	}

	public static boolean isCtrlPressed() {
		return Screen.hasControlDown();
	}

	public static boolean isShiftPressed() {
		return Screen.hasShiftDown();
	}

}
