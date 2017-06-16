package me.deftware.client.framework.Wrappers;

import net.minecraft.client.Minecraft;

public class IInventory {

	public static int getFirstEmptyStack() {
		return Minecraft.getMinecraft().player.inventory.getFirstEmptyStack();
	}

	public static int getCurrentItem() {
		return Minecraft.getMinecraft().player.inventory.currentItem;
	}

	public static void setCurrentItem(int id) {
		Minecraft.getMinecraft().player.inventory.currentItem = id;
	}

}
