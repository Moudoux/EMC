package me.deftware.client.framework.Wrappers.Render;

import me.deftware.client.framework.Wrappers.Item.IItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;

public class IEnchantmentHelper {

	public static int getEnchantmentLevel(int enchantID, IItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(16), stack.getStack());
	}

}
