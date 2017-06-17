package me.deftware.client.framework.Wrappers.Item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;

public class IItem {

	private Item item;

	public IItem(Item item) {
		this.item = item;
	}

	public IItem(String name) {
		this.item = Item.getByNameOrId(name);
	}

	public Item getItem() {
		return item;
	}

	public String getName() {
		return item.getItemStackDisplayName(new ItemStack(item));
	}

	public int getID() {
		return item.getIdFromItem(item);
	}

	public boolean isValidItem() {
		return this.item != null;
	}

	public boolean isThrowable() {
		if (item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg
				|| item instanceof ItemEnderPearl || item instanceof ItemSplashPotion
				|| item instanceof ItemLingeringPotion || item instanceof ItemFishingRod) {
			return true;
		}
		return false;
	}

	public boolean instanceOf(IItemType type) {
		if (type.equals(IItemType.ItemFishingRod)) {
			return item instanceof ItemFishingRod;
		} else if (type.equals(IItemType.ItemPotion)) {
			return item instanceof ItemPotion;
		}
		return false;
	}

	public static enum IItemType {
		ItemPotion, ItemFishingRod
	}

}
