package me.deftware.client.framework.wrappers.item;

import net.minecraft.container.Slot;

public class ISlot {

    private Slot slot;

    public ISlot(Slot slot) {
        this.slot = slot;
    }

    public int getIID() {
        return slot.id;
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
