package me.deftware.client.framework.wrappers.item.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ITotem {


	public static int getSlot() {
		InventoryPlayer in = Minecraft.getMinecraft().player.inventory;
		for (int i = 0; i < in.mainInventory.size() + 1; i++) {
			try {
				ItemStack it = in.mainInventory.get(i);
				if (Item.getIdFromItem(it.getItem()) == 449) {
					return i;
				}
			} catch (Exception ex) {
			}
		}
		return -1;
	}


	public static boolean hasTotem() {
		InventoryPlayer in = Minecraft.getMinecraft().player.inventory;
		for (int i = 0; i < in.mainInventory.size() + 1; i++) {
			try {
				ItemStack it = in.mainInventory.get(i);
				if (Item.getIdFromItem(it.getItem()) == 449) {
					return true;
				}
			} catch (Exception ex) {
			}
		}
		return false;
	}

	public static void swapItems(int one, int two, int windowId) {
		Minecraft.getMinecraft().playerController.windowClick(windowId, one, 0, ClickType.SWAP,
				Minecraft.getMinecraft().player);
		Minecraft.getMinecraft().playerController.windowClick(windowId, two, 0, ClickType.SWAP,
				Minecraft.getMinecraft().player);
		Minecraft.getMinecraft().playerController.updateController();
	}

	public static void moveItem() {
		Minecraft.getMinecraft().playerController.windowClick(0, ITotem.getSlot(), 0, ClickType.QUICK_MOVE,
				Minecraft.getMinecraft().player);
	}

}
