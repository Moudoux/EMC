package me.deftware.client.framework.wrappers.item;


import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.entity.IEntityPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.container.Slot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.packet.CreativeInventoryActionC2SPacket;

import java.util.ArrayList;
import java.util.Collections;

public class IInventoryWrapper {

    public static ArrayList<IItemStack> getArmorInventory(IEntity entity) {
        ArrayList<IItemStack> array = new ArrayList<>();
        if (entity != null) {
            for (ItemStack item : entity.getEntity().getArmorItems()) {
                if (item != null) {
                    IItemStack stack = new IItemStack(item);
                    array.add(stack);
                }
            }
        }

        Collections.reverse(array);
        return array;
    }

    public static boolean hasElytra() {
        if (IEntityPlayer.isNull()) {
            return false;
        }
        ItemStack chest = MinecraftClient.getInstance().player.getEquippedStack(EquipmentSlot.CHEST);
        if (chest != null) {
            return chest.getItem() == Items.ELYTRA;
        }
        return false;
    }

    public static boolean placeStackInHotbar(IItemStack stack) {
        for (int i = 0; i < 9; i++) {
            if (IInventoryWrapper.getStackInSlot(i).isEmpty()) {
                MinecraftClient.getInstance().player.networkHandler
                        .sendPacket(new CreativeInventoryActionC2SPacket(36 + i, stack.getStack()));
                return true;
            }
        }

        return false;
    }

    public static IItemStack getHeldItem(IEntity entity, boolean offhand) {
        ItemStack finalItem = ItemStack.EMPTY;
        int slotId = 0;
        if (entity != null) {
            for (ItemStack item : entity.getEntity().getItemsHand()) {
                if ((slotId == 0 && !offhand) || (slotId == 1 && offhand)) {
                    finalItem = item;
                    break;
                } else if (slotId <= 1) {
                    slotId++;
                } else {
                    break;
                }
            }
        }
        return new IItemStack(finalItem);
    }

    public static IItemStack getHeldInventoryItem() {
        return new IItemStack(MinecraftClient.getInstance().player.inventory.getMainHandStack());
    }

    public static IItemStack getHeldItem(boolean offhand) {
        return IInventoryWrapper.getHeldItem(new IEntity(MinecraftClient.getInstance().player), offhand);
    }

    public static ArrayList<ISlot> getSlots() {
        if (IEntityPlayer.isNull()) {
            return new ArrayList<>();
        }
        ArrayList<ISlot> slots = new ArrayList<>();
        for (Slot d : MinecraftClient.getInstance().player.container.slots) {
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
