package me.deftware.client.framework.wrappers.item;


import me.deftware.client.framework.wrappers.entity.IEntityPlayer;
import me.deftware.client.framework.wrappers.entity.IPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.container.Slot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.packet.CreativeInventoryActionServerPacket;

import java.util.ArrayList;

public class IInventoryWrapper {

    public static ArrayList<IItemStack> getArmorInventory(IPlayer player) {
        if (IEntityPlayer.isNull()) {
            return new ArrayList<>();
        }
        ArrayList<IItemStack> array = new ArrayList<>();
        for (int index = 3; index >= 0; index--) {
            ItemStack item = player.getPlayer().inventory.armor.get(index);
            IItemStack stack = new IItemStack(item);
            array.add(stack);
        }
        return array;
    }

    public static boolean hasElytra() {
        if (IEntityPlayer.isNull()) {
            return false;
        }
        ItemStack chest = MinecraftClient.getInstance().player.getEquippedStack(EquipmentSlot.CHEST);
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
                MinecraftClient.getInstance().player.networkHandler
                        .sendPacket(new CreativeInventoryActionServerPacket(36 + i, stack.getStack()));
                return true;
            }
        }

        return false;
    }

    public static IItemStack getHeldItem(IPlayer player, boolean offhand) {
        if (IEntityPlayer.isNull()) {
            return null;
        }
        ItemStack item = offhand ? player.getPlayer().getOffHandStack() : player.getPlayer().getMainHandStack();
        return new IItemStack(item);
    }

    public static IItemStack getHeldInventoryItem() {
        return new IItemStack(MinecraftClient.getInstance().player.inventory.getMainHandStack());
    }

    public static IItemStack getHeldItem(boolean offhand) {
        return IInventoryWrapper.getHeldItem(new IPlayer(MinecraftClient.getInstance().player), offhand);
    }

    public static ArrayList<ISlot> getSlots() {
        if (IEntityPlayer.isNull()) {
            return new ArrayList<>();
        }
        ArrayList<ISlot> slots = new ArrayList<>();
        for (Slot d : MinecraftClient.getInstance().player.containerPlayer.slotList) {
            slots.add(new ISlot(d));
        }
        return slots;
    }

    public static IItemStack getArmorInventorySlot(int id) {
        if (IEntityPlayer.isNull()) {
            return null;
        }
        return new IItemStack(MinecraftClient.getInstance().player.inventory.armor.get(id));
    }

    public static IItemStack getArmorInSlot(int id) {
        if (IEntityPlayer.isNull()) {
            return null;
        }
        return new IItemStack(MinecraftClient.getInstance().player.inventory.getArmorStack(id));
    }

    public static IItemStack getStackInSlot(int id) {
        if (IEntityPlayer.isNull()) {
            return null;
        }
        return new IItemStack(MinecraftClient.getInstance().player.inventory.getInvStack(id));
    }

    public static int getFirstEmptyStack() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.inventory.getEmptySlot();
    }

}
