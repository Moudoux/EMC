package me.deftware.client.framework.wrappers;


import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@SuppressWarnings("RedundantConditionalExpression")
public class IMouse {

    public static ArrayList<MouseScrollCallback> scrollCallbacks = new ArrayList<>();

    /**
     * @see GLFW#GLFW_MOUSE_BUTTON_LEFT
     * @see GLFW#GLFW_MOUSE_BUTTON_RIGHT
     * @see GLFW#GLFW_MOUSE_BUTTON_MIDDLE
     */
    public static void clickMouse(int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            ((IMixinMinecraft) MinecraftClient.getInstance()).doClickMouse();
        } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            ((IMixinMinecraft) MinecraftClient.getInstance()).doRightClickMouse();
        } else if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
            ((IMixinMinecraft) MinecraftClient.getInstance()).doMiddleClickMouse();
        }
    }

    public static boolean isButtonDown(int button) {
        if (button == 0) {
            return GLFW.glfwGetMouseButton(MinecraftClient.getInstance().window.getHandle(),
                    GLFW.GLFW_MOUSE_BUTTON_1) == 1 ? true : false;
        } else if (button == 1) {
            return GLFW.glfwGetMouseButton(MinecraftClient.getInstance().window.getHandle(),
                    GLFW.GLFW_MOUSE_BUTTON_2) == 1 ? true : false;
        }
        return false;
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
