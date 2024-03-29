package me.deftware.client.framework.helper;

import me.deftware.client.framework.gui.GuiScreen;
import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.client.framework.render.shader.Shader;
import me.deftware.client.framework.util.minecraft.MinecraftIdentifier;
import me.deftware.mixin.imp.IMixinEntityRenderer;
import net.minecraft.client.MinecraftClient;

/**
 * @author Deftware
 */
public class WindowHelper {

	public static int getLimitFramerate() {
		return MinecraftClient.getInstance().options.maxFps;
	}

	public static void setLimitFramerate(int framerate) {
		MinecraftClient.getInstance().options.maxFps = framerate;
		MinecraftClient.getInstance().getWindow().setFramerateLimit(framerate);
	}

	public static long getWindowHandle() {
		return MinecraftClient.getInstance().getWindow().getHandle();
	}

	public static double getScaleFactor() {
		return MinecraftClient.getInstance().getWindow().getScaleFactor();
	}

	public static void setScaleFactor(int factor) {
		MinecraftClient.getInstance().options.guiScale = factor;
		MinecraftClient.getInstance().onResolutionChanged();
	}

	public static int getGuiScaleRaw() {
		return MinecraftClient.getInstance().options.guiScale;
	}

	public static int getGuiScale() {
		int factor = MinecraftClient.getInstance().getWindow().calculateScaleFactor(MinecraftClient.getInstance().options.guiScale, MinecraftClient.getInstance().forcesUnicodeFont());
		if (factor == 0) {
			factor = 4;
		}
		return factor;
	}

	public static boolean isFocused() {
		return MinecraftClient.getInstance().isWindowFocused();
	}

	public static boolean isMinimized() {
		return GuiScreen.getDisplayHeight() == 0 && GuiScreen.getDisplayWidth() == 0;
	}

	public static int getFPS() {
		return Minecraft.getMinecraftGame().getFPS();
	}

	public static boolean isDebugInfoShown() {
		return MinecraftClient.getInstance().options.debugEnabled;
	}

	public static double getGamma() {
		return MinecraftClient.getInstance().options.gamma;
	}

	public static void setGamma(double value) {
		MinecraftClient.getInstance().options.gamma = value;
	}

	@Deprecated
	public static void loadShader(MinecraftIdentifier location) {
		((IMixinEntityRenderer) MinecraftClient.getInstance().gameRenderer).loadCustomShader(location);
	}

	public static void loadShader(Shader shader) {
		((IMixinEntityRenderer) MinecraftClient.getInstance().gameRenderer).loadShader(shader);
	}

	public static void disableShader() {
		MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().gameRenderer.disableShader());
	}

}
