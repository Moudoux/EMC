package me.deftware.client.framework.inventory;

import me.deftware.client.framework.conversion.ComparedConversion;
import me.deftware.client.framework.conversion.ConvertedList;
import me.deftware.client.framework.item.Item;
import me.deftware.client.framework.item.ItemStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

import java.util.List;

/**
 * @author Deftware
 */
public class Inventory {

	protected final ConvertedList<ItemStack, net.minecraft.item.ItemStack> armourInventory, mainInventory;
	protected final ComparedConversion<net.minecraft.item.ItemStack, ItemStack> mainHand, offHand;
	protected final PlayerEntity entity;

	public Inventory(PlayerEntity entity) {
		this.entity = entity;
		this.mainHand = new ComparedConversion<>(entity::getMainHandStack, ItemStack::new);
		this.offHand = new ComparedConversion<>(entity::getOffHandStack, ItemStack::new);

		this.armourInventory = new ConvertedList<>(() -> entity.getInventory().armor, pair ->
			pair.getLeft().getMinecraftItemStack() == entity.getInventory().armor.get(pair.getRight())
		, ItemStack::new);

		this.mainInventory = new ConvertedList<>(() -> entity.getInventory().main, pair ->
				net.minecraft.item.ItemStack.areEqual(pair.getLeft().getMinecraftItemStack(), entity.getInventory().main.get(pair.getRight()))
				, ItemStack::new);
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
		return entity.getInventory().size();
	}

	public List<ItemStack> getArmourInventory() {
		return armourInventory.poll();
	}

	public List<ItemStack> getMainInventory() {
		return mainInventory.poll();
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
		return offhand ? this.offHand.get() : this.mainHand.get();
	}

	public ItemStack getStackInSlot(int slotId) {
		return new ItemStack(entity.getInventory().getStack(slotId));
	}

	public ItemStack getStackInArmourSlot(int slotId) {
		return new ItemStack(entity.getInventory().getArmorStack(slotId));
	}

	public boolean hasElytra() {
		net.minecraft.item.ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);
		return chest != null && chest.getItem() == Items.ELYTRA;
	}

}
