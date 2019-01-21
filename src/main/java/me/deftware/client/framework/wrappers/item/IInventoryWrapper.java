package me.deftware.client.framework.wrappers.item;


import me.deftware.client.framework.wrappers.entity.IEntityPlayer;
import me.deftware.client.framework.wrappers.entity.IPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;

import java.util.ArrayList;

public class IInventoryWrapper {

	public static ArrayList<IItemStack> getArmorInventory(IPlayer player) {
		if (IEntityPlayer.isNull()) {
			return new ArrayList<>();
		}
		ArrayList<IItemStack> array = new ArrayList<>();
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
		ItemStack chest = Minecraft.getInstance().player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		if (chest != null) {
			if (chest.getItem() == Items.ELYTRA) {
				return true;
			}
		}
		return false;
	}

	public static boolean placeStackInHotbar(IItemStack stack) {
		for (int i = 0; i < 9; i++) {
			if (IInventoryWrapper.getStackInSlot(i).isEmpty()) {
				Minecraft.getInstance().player.connection
						.sendPacket(new CPacketCreativeInventoryAction(36 + i, stack.getStack()));
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
		return IInventoryWrapper.getHeldItem(new IPlayer(Minecraft.getInstance().player), offhand);
	}

	public static ArrayList<ISlot> getSlots() {
		if (IEntityPlayer.isNull()) {
			return new ArrayList<>();
		}
		ArrayList<ISlot> slots = new ArrayList<>();
		for (Slot d : Minecraft.getInstance().player.inventoryContainer.inventorySlots) {
			slots.add(new ISlot(d));
		}
		return slots;
	}

	public static IItemStack getArmorInventorySlot(int id) {
		if (IEntityPlayer.isNull()) {
			return null;
		}
		return new IItemStack(Minecraft.getInstance().player.inventory.armorInventory.get(id));
	}

	public static IItemStack getArmorInSlot(int id) {
		if (IEntityPlayer.isNull()) {
			return null;
		}
		return new IItemStack(Minecraft.getInstance().player.inventory.armorItemInSlot(id));
	}

	public static IItemStack getStackInSlot(int id) {
		if (IEntityPlayer.isNull()) {
			return null;
		}
		return new IItemStack(Minecraft.getInstance().player.inventory.getStackInSlot(id));
	}

	public static int getFirstEmptyStack() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.inventory.getFirstEmptyStack();
	}

}
