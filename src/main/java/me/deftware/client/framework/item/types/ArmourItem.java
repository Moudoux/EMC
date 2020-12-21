package me.deftware.client.framework.item.types;

import me.deftware.client.framework.item.Item;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

import java.util.Collections;

/**
 * @author Deftware
 */
public class ArmourItem extends Item {

	private final Iterable<ItemStack> stack;

	public ArmourItem(net.minecraft.item.Item item) {
		super(item);
		this.stack = Collections.singletonList(new ItemStack(getMinecraftItem()));
	}

	@Override
	public ArmorItem getMinecraftItem() {
		return (ArmorItem) item;
	}

	public int getDamageReduceAmount() {
		int enchantedAmount = EnchantmentHelper.getProtectionAmount(stack, DamageSource.GENERIC);
		if (enchantedAmount == 0) { // Returns 0 if the item has no enchantments
			return getItemProtectionAmount();
		}
		return enchantedAmount;
	}

	public int getItemProtectionAmount() {
		return getMinecraftItem().getProtection();
	}

	public int getTypeOrdinal() {
		return getMinecraftItem().getSlotType().ordinal();
	}

}
