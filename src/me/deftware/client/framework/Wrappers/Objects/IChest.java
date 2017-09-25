package me.deftware.client.framework.Wrappers.Objects;

import me.deftware.client.framework.Wrappers.Item.IItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ContainerChest;

public class IChest {

	private static ContainerChest getCurrent() {
		if (Minecraft.getMinecraft().player.openContainer == null) {
			return null;
		}
		return (ContainerChest) Minecraft.getMinecraft().player.openContainer;
	}

	public static int getInventorySize() {
		if (getCurrent() == null) {
			return 0;
		}
		return getCurrent().getLowerChestInventory().getSizeInventory();
	}

	public static IItemStack getStackInSlot(int id) {
		return new IItemStack(getCurrent().getLowerChestInventory().getStackInSlot(id));
	}

	public static int getContainerID() {
		return getCurrent().windowId;
	}

	public static boolean isEmpty() {
		return getCurrent().getLowerChestInventory().func_191420_l();
	}

}
