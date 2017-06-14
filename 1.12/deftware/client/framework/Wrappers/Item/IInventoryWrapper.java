package me.deftware.client.framework.Wrappers.Item;

import java.util.ArrayList;

import me.deftware.client.framework.Wrappers.Entity.IPlayer;
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

}
