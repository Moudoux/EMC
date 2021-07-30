package me.deftware.client.framework.inventory;

import me.deftware.client.framework.item.Item;
import me.deftware.client.framework.item.ItemStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Deftware
 */
public class Inventory {

	/**
	 * 36 (Main) + 4 (Armor) + 1 (Offhand)
	 */
	protected final List<ItemStack> main = DefaultedList.ofSize(36, ItemStack.EMPTY), armor = DefaultedList.ofSize(4, ItemStack.EMPTY), combined = new ArrayList<>();

	protected ItemStack offhand;

	protected final PlayerEntity entity;

	public Inventory(PlayerEntity entity) {
		this.entity = entity;
		ItemStack.init(entity.getInventory().main, main);
		ItemStack.init(entity.getInventory().armor, armor);
		offhand = new ItemStack(entity.getOffHandStack());
		combined.addAll(main);
		combined.addAll(armor);
		combined.add(offhand);
	}

	public int findItem(Item item) {
		for (int i = 0; i < entity.getInventory().size(); i++) {
			net.minecraft.item.ItemStack it = entity.getInventory().getStack(i);
			if (it.getItem().getTranslationKey().equals(item.getTranslationKey())) {
				return i;
			}
		}
		return -1;
	}

	public int getSize() {
		return combined.size();
	}

	public List<ItemStack> getArmourInventory() {
		return armor;
	}

	public List<ItemStack> getMainInventory() {
		return main;
	}
	
	public int getFirstEmptyStack() {
		return entity.getInventory().getEmptySlot();
	}

	public int getCurrentItem() {
		return entity.getInventory().selectedSlot;
	}

	public int getFirstEmptySlot() {
		return entity.getInventory().getEmptySlot();
	}

	public void setCurrentItem(int id) {
		entity.getInventory().selectedSlot = id;
	}

	public ItemStack getHeldItem(boolean offhand) {
		if (offhand)
			return this.offhand.setStack(entity.getOffHandStack());
		return main.get(getCurrentItem()).setStack(entity.getInventory().getStack(getCurrentItem()));
	}

	public ItemStack getStackInSlot(int slotId) {
		if (slotId >= combined.size())
			return ItemStack.EMPTY;
		return combined.get(slotId).setStack(entity.getInventory().getStack(slotId));
	}

	public ItemStack getStackInArmourSlot(int slotId) {
		if (slotId >= armor.size())
			return ItemStack.EMPTY;
		return armor.get(slotId).setStack(entity.getInventory().armor.get(slotId));
	}

	public boolean hasElytra() {
		net.minecraft.item.ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);
		return chest != null && chest.getItem() == Items.ELYTRA;
	}

}
