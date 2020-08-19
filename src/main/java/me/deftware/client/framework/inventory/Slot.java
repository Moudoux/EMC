package me.deftware.client.framework.inventory;

import me.deftware.client.framework.item.ItemStack;

/**
 * @author Deftware
 */
public class Slot {

	protected final net.minecraft.screen.slot.Slot slot;
	private ItemStack cachedItemStack;

	public Slot(net.minecraft.screen.slot.Slot slot) {
		this.slot = slot;
		this.cachedItemStack = new ItemStack(slot.getStack());
	}

	public net.minecraft.screen.slot.Slot getMinecraftSlot() {
		return slot;
	}

	public int getId() {
		return slot.id;
	}

	public ItemStack getStack() {
		if (cachedItemStack.getMinecraftItemStack() != slot.getStack()) {
			this.cachedItemStack = new ItemStack(slot.getStack());
		}
		return cachedItemStack;
	}

}
