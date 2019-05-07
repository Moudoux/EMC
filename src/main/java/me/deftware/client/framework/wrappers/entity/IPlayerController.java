package me.deftware.client.framework.wrappers.entity;

import me.deftware.mixin.imp.IMixinPlayerControllerMP;
import net.minecraft.client.MinecraftClient;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class IPlayerController {

    public static void windowClick(int id, int next, IClickType type) {
        windowClick(0, id, next, type);
    }

    public static void windowClick(int windowID, int id, int next, IClickType type) {
        MinecraftClient.getInstance().interactionManager.method_2906(windowID, id, next,
                type.equals(IClickType.THROW) ? SlotActionType.THROW :
                        type.equals(IClickType.QUICK_MOVE) ? SlotActionType.QUICK_MOVE :
                                SlotActionType.PICKUP,
                MinecraftClient.getInstance().player);
    }

    /**
     * Looks for an item in the inventory and returns the slot id where the item is located,
     * returns -1 if the player does not have the item in their inventory
     */
    public static int getSlot(String name) {
        PlayerInventory in = MinecraftClient.getInstance().player.inventory;
        for (int i = 0; i < in.main.size(); i++) {
            ItemStack it = in.main.get(i);
            if (it.getItem().getTranslationKey().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Swaps a stack in the same inventory
     */
    @SuppressWarnings("Duplicates")
    public static void swapStack(int one, int two, int windowId) {
        MinecraftClient.getInstance().interactionManager.method_2906(windowId, one, 0, SlotActionType.SWAP,
                MinecraftClient.getInstance().player);
        MinecraftClient.getInstance().interactionManager.method_2906(windowId, two, 0, SlotActionType.SWAP,
                MinecraftClient.getInstance().player);
        MinecraftClient.getInstance().interactionManager.tick();
    }

    /**
     * Swaps a stack between one inventory to another
     */
    @SuppressWarnings("Duplicates")
    public static void swapStack(int srcInventoryId, int dstInventoryId, int srcSlot, int dstSlot) {
        MinecraftClient.getInstance().interactionManager.method_2906(srcInventoryId, srcSlot, 0,
                SlotActionType.SWAP, MinecraftClient.getInstance().player);
        MinecraftClient.getInstance().interactionManager.method_2906(dstInventoryId, dstSlot, 0,
                SlotActionType.SWAP, MinecraftClient.getInstance().player);
        MinecraftClient.getInstance().interactionManager.tick();
    }

    public static void moveStack(int srcInventoryId, int dstInventoryId, int srcSlot, int dstSlot) {
        MinecraftClient.getInstance().interactionManager.method_2906(srcInventoryId, srcSlot, 0,
                SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);
        MinecraftClient.getInstance().interactionManager.method_2906(dstInventoryId, dstSlot, 0,
                SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);
    }

    public static void moveItem(int slotId) {
        MinecraftClient.getInstance().interactionManager.method_2906(0, slotId, 0, SlotActionType.QUICK_MOVE,
                MinecraftClient.getInstance().player);
    }

    public static void processRightClick(boolean offhand) {
        MinecraftClient.getInstance().interactionManager.interactItem(MinecraftClient.getInstance().player, MinecraftClient.getInstance().world, offhand ? Hand.OFF_HAND : Hand.MAIN_HAND);
    }

    public static void resetBlockRemoving() {
        MinecraftClient.getInstance().interactionManager.cancelBlockBreaking();
    }

    public static void setPlayerHittingBlock(boolean state) {
        ((IMixinPlayerControllerMP) MinecraftClient.getInstance().interactionManager).setPlayerHittingBlock(state);
    }

    public enum IClickType {
        THROW, QUICK_MOVE, PICKUP
    }

}
