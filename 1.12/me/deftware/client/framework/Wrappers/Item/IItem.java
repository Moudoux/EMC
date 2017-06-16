package me.deftware.client.framework.Wrappers.Item;

import net.minecraft.item.Item;

public class IItem {

	private Item item;

	public IItem(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

}
