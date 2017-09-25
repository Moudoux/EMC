package me.deftware.client.framework.Wrappers.Objects;

import me.deftware.client.framework.Wrappers.Item.IItem;
import net.minecraft.inventory.Slot;

public class ISlot {

	private Slot slot;

	public ISlot(Slot slot) {
		this.slot = slot;
	}

	public int getIID() {
		return slot.slotNumber;
	}

	public IItem getIItem() {
		return new IItem(slot.getStack().getItem());
	}

}
