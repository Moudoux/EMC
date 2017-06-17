package me.deftware.client.framework.Wrappers.Item;

import java.util.ArrayList;

import me.deftware.client.framework.Wrappers.Entity.IPlayer;
import me.deftware.client.framework.Wrappers.Objects.ISlot;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class IInventoryWrapper {

	/**
	 * Get's a given IPlayer's armor inventory and converts it to a IItemStack
	 * 
	 * @param player
	 * @return
	 */
	public static ArrayList<IItemStack> getArmorInventory(IPlayer player) {
		ArrayList<IItemStack> array = new ArrayList<IItemStack>();
		for (int index = 3; index >= 0; index--) {
			ItemStack item = player.getPlayer().inventory.armorInventory.get(index);
			IItemStack stack = new IItemStack(item);
			array.add(stack);
		}
		return array;
	}

	/**
	 * Get's a given IPlayer's armor inventory and converts it to a IItemStack
	 * 
	 * @param player
	 * @return
	 */
	public static IItemStack getHeldItem(IPlayer player, boolean offhand) {
		ItemStack item = offhand ? player.getPlayer().getHeldItemOffhand() : player.getPlayer().getHeldItemMainhand();
		IItemStack stack = new IItemStack(item);
		return stack;
	}

	public static ArrayList<ISlot> getSlots() {
		ArrayList<ISlot> slots = new ArrayList<ISlot>();
		for (Slot d : Minecraft.getMinecraft().player.inventoryContainer.inventorySlots) {
			slots.add(new ISlot(d));
		}
		return slots;
	}

	public static IItemStack getArmorInventorySlot(int id) {
		return new IItemStack(Minecraft.getMinecraft().player.inventory.armorInventory.get(id));
	}

	public static IItemStack getArmorInSlot(int id) {
		return new IItemStack(Minecraft.getMinecraft().player.inventory.armorItemInSlot(id));
	}

	public static IItemStack getStackInSlot(int id) {
		return new IItemStack(Minecraft.getMinecraft().player.inventory.getStackInSlot(id));
	}

	public static int getFirstEmptyStack() {
		return Minecraft.getMinecraft().player.inventory.getFirstEmptyStack();
	}

}
