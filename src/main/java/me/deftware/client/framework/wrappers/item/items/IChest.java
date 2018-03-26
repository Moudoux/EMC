package me.deftware.client.framework.wrappers.item.items;

import me.deftware.client.framework.wrappers.item.IItemStack;
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
		if (IChest.getCurrent() == null) {
			return 0;
		}
		return IChest.getCurrent().getLowerChestInventory().getSizeInventory();
	}

	public static IItemStack getStackInSlot(int id) {
		return new IItemStack(IChest.getCurrent().getLowerChestInventory().getStackInSlot(id));
	}

	public static int getContainerID() {
		return IChest.getCurrent().windowId;
	}

	public static boolean isEmpty() {
		return IChest.getCurrent().getLowerChestInventory().isEmpty();
	}

}
