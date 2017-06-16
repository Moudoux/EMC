package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ITotem {

	/**
	 * Returns the slot a play has a totem in
	 * 
	 * @return
	 */
	public static int getSlot() {
		InventoryPlayer in = Minecraft.getMinecraft().player.inventory;
		for (int i = 0; i < in.mainInventory.size() + 1; i++) {
			try {
				ItemStack it = in.mainInventory.get(i);
				if (Item.getIdFromItem(it.getItem()) == 449) {
					return i;
				}
			} catch (Exception ex) {
				;
			}
		}
		return -1;
	}

	/**
	 * Does the user have a totem ?
	 * 
	 * @return
	 */
	public static boolean hasTotem() {
		InventoryPlayer in = Minecraft.getMinecraft().player.inventory;
		for (int i = 0; i < in.mainInventory.size() + 1; i++) {
			try {
				ItemStack it = in.mainInventory.get(i);
				if (Item.getIdFromItem(it.getItem()) == 449) {
					return true;
				}
			} catch (Exception ex) {
				;
			}
		}
		return false;
	}

	/**
	 * Is the main slot used ? (Slot: 0)
	 */
	public static boolean mainSlotUsed() {
		InventoryPlayer in = Minecraft.getMinecraft().player.inventory;
		for (int i = 0; i < in.mainInventory.size() + 1; i++) {
			try {
				ItemStack it = in.mainInventory.get(i);
				if (i == 0 && it.getDisplayName().equals("Air")) {
					return false;
				}
			} catch (Exception ex) {
				;
			}
		}
		return true;
	}

	/**
	 * Set totem to hand
	 */
	public static void rearrangeInventory() {
		try {
			if (Minecraft.getMinecraft().player.inventory.getFirstEmptyStack() != -1) {
				if (mainSlotUsed() && getSlot() != 0) {
					Minecraft.getMinecraft().playerController.windowClick(0, 0, 0, ClickType.SWAP,
							Minecraft.getMinecraft().player);
					Minecraft.getMinecraft().playerController.windowClick(0,
							Minecraft.getMinecraft().player.inventory.getFirstEmptyStack(),
							0, ClickType.SWAP, Minecraft.getMinecraft().player);
					Minecraft.getMinecraft().playerController.updateController();
				} else {
					Minecraft.getMinecraft().playerController.windowClick(0, getSlot(), 0, ClickType.QUICK_MOVE,
							Minecraft.getMinecraft().player);
				}
			}
		} catch (Exception ex) {
			;
		}
	}


}
