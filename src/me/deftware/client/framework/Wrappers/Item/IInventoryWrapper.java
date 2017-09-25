package me.deftware.client.framework.Wrappers.Item;

import java.util.ArrayList;

import me.deftware.client.framework.Wrappers.Entity.IEntityPlayer;
import me.deftware.client.framework.Wrappers.Entity.IPlayer;
import me.deftware.client.framework.Wrappers.Objects.ISlot;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class IInventoryWrapper {

	
	public static ArrayList<IItemStack> getArmorInventory(IPlayer player) {
		if (IEntityPlayer.isNull()) {
			return new ArrayList<IItemStack>();
		}
		ArrayList<IItemStack> array = new ArrayList<IItemStack>();
		for (int index = 3; index >= 0; index--) {
			ItemStack item = player.getPlayer().inventory.armorInventory.get(index);
			IItemStack stack = new IItemStack(item);
			array.add(stack);
		}
		return array;
	}
	
	public static boolean hasElytra() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		ItemStack chest = Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		if (chest != null) {
			if (chest.getItem() == Items.ELYTRA) {
				return true;
			}
		}
		return false;
	}

	
	public static IItemStack getHeldItem(IPlayer player, boolean offhand) {
		if (IEntityPlayer.isNull()) {
			return null;
		}
		ItemStack item = offhand ? player.getPlayer().getHeldItemOffhand() : player.getPlayer().getHeldItemMainhand();
		IItemStack stack = new IItemStack(item);
		return stack;
	}

	public static IItemStack getHeldItem(boolean offhand) {
		return getHeldItem(new IPlayer(Minecraft.getMinecraft().player), offhand);
	}

	public static ArrayList<ISlot> getSlots() {
		if (IEntityPlayer.isNull()) {
			return new ArrayList<ISlot>();
		}
		ArrayList<ISlot> slots = new ArrayList<ISlot>();
		for (Slot d : Minecraft.getMinecraft().player.inventoryContainer.inventorySlots) {
			slots.add(new ISlot(d));
		}
		return slots;
	}

	public static IItemStack getArmorInventorySlot(int id) {
		if (IEntityPlayer.isNull()) {
			return null;
		}
		return new IItemStack(Minecraft.getMinecraft().player.inventory.armorInventory.get(id));
	}

	public static IItemStack getArmorInSlot(int id) {
		if (IEntityPlayer.isNull()) {
			return null;
		}
		return new IItemStack(Minecraft.getMinecraft().player.inventory.armorItemInSlot(id));
	}

	public static IItemStack getStackInSlot(int id) {
		if (IEntityPlayer.isNull()) {
			return null;
		}
		return new IItemStack(Minecraft.getMinecraft().player.inventory.getStackInSlot(id));
	}

	public static int getFirstEmptyStack() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.inventory.getFirstEmptyStack();
	}

}
