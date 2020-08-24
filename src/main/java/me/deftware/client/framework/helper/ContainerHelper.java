package me.deftware.client.framework.helper;

import me.deftware.client.framework.item.ItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.GenericContainerScreenHandler;

import java.util.Objects;

/**
 * @author Deftware
 */
public class ContainerHelper {

	private static GenericContainerScreenHandler getCurrent() {
		if (Objects.requireNonNull(MinecraftClient.getInstance().player).currentScreenHandler != null && MinecraftClient.getInstance().player.currentScreenHandler instanceof GenericContainerScreenHandler) {
			return (GenericContainerScreenHandler) MinecraftClient.getInstance().player.currentScreenHandler;
		}
		return null;
	}

	public static boolean isOpen() {
		return getCurrent() != null;
	}

	public static int getInventorySize() {
		return Objects.requireNonNull(getCurrent()).getInventory().size();
	}

	public static ItemStack getStackInSlot(int id) {
		return new ItemStack(Objects.requireNonNull(getCurrent()).getInventory().getStack(id));
	}

	public static int getContainerID() {
		return Objects.requireNonNull(getCurrent()).syncId;
	}

	public static boolean isEmpty() {
		return Objects.requireNonNull(getCurrent()).getInventory().isEmpty();
	}

	public static int getMaxSlots() {
		return Objects.requireNonNull(getCurrent()).slots.size();
	}

}
