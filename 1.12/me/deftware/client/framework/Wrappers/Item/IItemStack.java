package me.deftware.client.framework.Wrappers.Item;

import me.deftware.client.framework.Wrappers.IBlock;
import me.deftware.client.framework.Wrappers.Item.Items.IItemArmor;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

public class IItemStack {

	private ItemStack stack;

	public IItemStack(ItemStack stack) {
		this.stack = stack;
	}

	public IItemStack(IBlock block) {
		this.stack = new ItemStack(Item.getItemFromBlock(block.getBlock()));
	}

	public IItemStack(IItem item) {
		this.stack = new ItemStack(item.getItem());
	}

	public ItemStack getStack() {
		return stack;
	}

	public String getDisplayName() {
		return stack.getDisplayName();
	}

	public int getItemID() {
		return Item.getIdFromItem(this.stack.getItem());
	}

	public static IItemStack cloneWithoutEffects(IItemStack stack) {
		return new IItemStack(new ItemStack(Item.getItemById(Item.getIdFromItem(stack.getStack().getItem())),
				stack.getStack().stackSize));
	}

	public IItem getIItem() {
		if (stack.getItem() instanceof ItemArmor) {
			return new IItemArmor(stack.getItem());
		}
		return new IItem(stack.getItem());
	}

	/**
	 * 0 = COMMON 1 = UNCOMMON 2 = RARE 3 = EPIC
	 * 
	 * @return
	 */
	public int getRarity() {
		if (stack.getRarity() == EnumRarity.COMMON) {
			return 0;
		} else if (stack.getRarity() == EnumRarity.UNCOMMON) {
			return 1;
		} else if (stack.getRarity() == EnumRarity.RARE) {
			return 2;
		} else if (stack.getRarity() == EnumRarity.EPIC) {
			return 3;
		}
		return 0;
	}

	public boolean isArmor() {
		if (this.stack.getItem() instanceof ItemArmor) {
			return true;
		}
		return false;
	}

	public boolean isBow() {
		if (this.stack.getItem() instanceof ItemBow) {
			return true;
		}
		return false;
	}

}
