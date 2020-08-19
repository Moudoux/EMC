package me.deftware.client.framework.helper;

import me.deftware.client.framework.gui.GuiScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.util.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * @author Deftware
 */
public class ScreenHelper {

	public static final List<Function<List<String>, List<String>>> debugHudModifiers = new CopyOnWriteArrayList<>();

	public static boolean isChatOpen() {
		if (MinecraftClient.getInstance().currentScreen != null) {
			return MinecraftClient.getInstance().currentScreen instanceof ChatScreen;
		}
		return false;
	}

	public static boolean isContainerOpen() {
		if (MinecraftClient.getInstance().currentScreen != null) {
			return MinecraftClient.getInstance().currentScreen instanceof HandledScreen<?>
					&& !(MinecraftClient.getInstance().currentScreen instanceof InventoryScreen);
		}
		return false;
	}

	public static boolean isInventoryOpen() {
		if (MinecraftClient.getInstance().currentScreen != null) {
			return MinecraftClient.getInstance().currentScreen instanceof HandledScreen<?>
					&& (MinecraftClient.getInstance().currentScreen instanceof InventoryScreen
					|| MinecraftClient.getInstance().currentScreen instanceof CreativeInventoryScreen);
		}
		return false;
	}

	/**
	 * For internal use only! Does NOT return a compatible {@link GuiScreen} for use in EMC mods!
	 *
	 * @return Returns an instance of a class in the current classpath, however it does NOT return a current instance, but a new one.
	 */
	@SafeVarargs
	@Nullable
	public static Screen createScreenInstance(Object clazz, Pair<Class<?>, Object>... constructorParameters) {
		try {
			Class<?> screenClass = clazz instanceof Class<?> ? (Class<?>) clazz : Class.forName((String) clazz);
			List<Class<?>> paramList = new ArrayList<>();
			List<Object> targetList = new ArrayList<>();
			Arrays.stream(constructorParameters).forEach(c -> {
				paramList.add(c.getLeft());
				targetList.add(c.getRight());
			});
			return (Screen) screenClass.getConstructor(paramList.toArray(new Class<?>[paramList.size()]))
					.newInstance(targetList.toArray(new Object[targetList.size()]));
		} catch (Exception ignored) { }
		return null;
	}

}
