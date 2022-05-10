package me.deftware.client.framework.wrappers.item;

import net.minecraft.inventory.Slot;

public class ISlot {

	private Slot slot;

	public ISlot(Slot slot) {
		this.slot = slot;
	}

	public int getIID() {
		return slot.slotNumber;
	}

	public IItemStack getIItemStack() {
		if (slot == null) {
			return null;
		}
		return new IItemStack(slot.getStack());
	}

	public IItem getIItem() {
		if (slot == null) {
			return null;
		}
		return new IItem(slot.getStack().getItem());
	}

}
