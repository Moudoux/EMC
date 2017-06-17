package me.deftware.client.framework.Wrappers.Item;

import net.minecraft.item.Item;
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

}
