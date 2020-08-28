package me.deftware.client.framework.helper;

import me.deftware.client.framework.item.ItemStack;
import me.deftware.mixin.imp.IMixinShulkerBoxScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;

import java.util.Objects;

/**
 * @author Deftware
 */
public class ContainerHelper {

	private static ScreenHandler getCurrent() {
		ScreenHandler handler = Objects.requireNonNull(MinecraftClient.getInstance().player).currentScreenHandler;
		if (handler != null && (handler instanceof GenericContainerScreenHandler || handler instanceof ShulkerBoxScreenHandler)) {
			return MinecraftClient.getInstance().player.currentScreenHandler;
		}
		return null;
	}

	public static boolean isOpen() {
		return getCurrent() != null;
	}

	public static Inventory getInventory() {
		ScreenHandler screenHandler = Objects.requireNonNull(getCurrent());
		if (screenHandler instanceof ShulkerBoxScreenHandler) {
			return ((IMixinShulkerBoxScreenHandler) screenHandler).getInventory();
		}
		return ((GenericContainerScreenHandler) screenHandler).getInventory();
	}

	public static boolean isDouble() {
		return getInventory() instanceof DoubleInventory;
	}

	public static int getInventorySize() {
		return getInventory().size();
	}

	public static ItemStack getStackInSlot(int id) {
		return new ItemStack(getInventory().getStack(id));
	}

	public static int getContainerID() {
		return Objects.requireNonNull(getCurrent()).syncId;
	}

	public static boolean isEmpty() {
		return getInventory().isEmpty();
	}

	public static int getMaxSlots() {
		return Objects.requireNonNull(getCurrent()).slots.size();
	}

}
