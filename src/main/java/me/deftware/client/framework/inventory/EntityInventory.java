package me.deftware.client.framework.inventory;

import me.deftware.client.framework.item.ItemStack;
import me.deftware.client.framework.util.Util;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

import java.util.List;

/**
 * @author Deftware
 */
public class EntityInventory extends Inventory {

	protected final List<ItemStack> main = Util.getEmptyStackList(36), armor = Util.getEmptyStackList(4);
	protected ItemStack offhand;

	private final PlayerEntity entity;

	public EntityInventory(PlayerEntity entity) {
		super(entity.getInventory());
		this.entity = entity;
		ItemStack.init(entity.getInventory().main, main);
		ItemStack.init(entity.getInventory().armor, armor);
		offhand = new ItemStack(entity.getOffHandStack());
		// Add items to delegate
		this.delegate.addAll(main);
		this.delegate.addAll(armor);
		this.delegate.add(offhand);
	}

	public List<ItemStack> getArmourInventory() {
		return armor;
	}

	public List<ItemStack> getMainInventory() {
		return main;
	}

	public int getCurrentItem() {
		return entity.getInventory().selectedSlot;
	}

	public void setCurrentItem(int id) {
		entity.getInventory().selectedSlot = id;
	}

	public ItemStack getHeldItem(boolean offhand) {
		if (offhand)
			return this.offhand.setStack(entity.getOffHandStack());
		return main.get(getCurrentItem()).setStack(entity.getInventory().getStack(getCurrentItem()));
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
