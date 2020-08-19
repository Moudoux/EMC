package me.deftware.client.framework.input;

import me.deftware.client.framework.helper.WindowHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author Deftware
 */
public class Keyboard {

	public static void registerKeyPolling() {
		GLFW.glfwSetCharCallback(WindowHelper.getWindowHandle(), new GLFWCharCallback() {
			@Override
			public void invoke(long window, int codepoint) {
				// TODO
			}
		});
	}

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
		if (normalKeys.containsKey(glfwCodePoint)) {
			return normalKeys.get(glfwCodePoint);
		} else if (functionKeys.containsKey(glfwCodePoint)) {
			return functionKeys.get(glfwCodePoint);
		}
		return "Unknown";
	}

	public static String getClipboardString() {
		return MinecraftClient.getInstance().keyboard.getClipboard();
	}

	public static void setClipboardString(String copyText) {
		MinecraftClient.getInstance().keyboard.setClipboard(copyText);
	}

	public static boolean isKeyDown(int key) {
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
