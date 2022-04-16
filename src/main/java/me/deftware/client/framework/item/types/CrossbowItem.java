package me.deftware.client.framework.item.types;

import me.deftware.client.framework.item.ItemStack;

/**
 * @author Deftware
 */
public class CrossbowItem extends RangedWeaponItem {

	public CrossbowItem(net.minecraft.item.Item item) {
		super(item);
	}

	public static boolean isCharged(ItemStack stack) {
		return net.minecraft.item.CrossbowItem.isCharged(stack.getMinecraftItemStack());
	}

}
