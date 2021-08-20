package me.deftware.client.framework.inventory;

import me.deftware.client.framework.item.Item;
import me.deftware.client.framework.item.ItemStack;
import me.deftware.client.framework.util.Util;
import net.minecraft.inventory.DoubleInventory;

import java.util.List;

/**
 * @author Deftware
 */
public class Inventory {

	protected final List<ItemStack> delegate;
	protected final net.minecraft.inventory.Inventory inventory;

	public Inventory(net.minecraft.inventory.Inventory inventory) {
		this.inventory = inventory;
		this.delegate = Util.getEmptyStackList(inventory.size());
		this.refresh();
	}

	public int findItem(Item item) {
		for (int i = 0; i < delegate.size(); i++) {
			ItemStack it = delegate.get(i);
			if (it.getItem().equals(item)) {
				return i;
			}
		}
		return -1;
	}

	public void refresh() {
		for (int i = 0; i < delegate.size(); i++)
			delegate.get(i).setStack(inventory.getStack(i));
	}

	public int getSize() {
		return delegate.size();
	}

	public boolean isEmpty() {
		return inventory.isEmpty();
	}

	public boolean isFull() {
		for (ItemStack itemStack : delegate)
			if (itemStack.isEmpty())
				return false;
		return true;
	}

	public boolean isDouble() {
		return inventory instanceof DoubleInventory;
	}

	public ItemStack getStackInSlot(int slotId) {
		if (slotId >= delegate.size())
			return ItemStack.EMPTY;
		return delegate.get(slotId).setStack(inventory.getStack(slotId));
	}

}
