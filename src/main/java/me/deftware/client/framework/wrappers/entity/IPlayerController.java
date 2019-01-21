package me.deftware.client.framework.wrappers.entity;

import me.deftware.mixin.imp.IMixinPlayerControllerMP;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class IPlayerController {

    public static void windowClick(int id, int next, IClickType type) {
        windowClick(0, id, next, type);
    }

    public static void windowClick(int windowID, int id, int next, IClickType type) {
        Minecraft.getInstance().playerController.windowClick(windowID, id, next,
                type.equals(IClickType.THROW) ? ClickType.THROW :
                        type.equals(IClickType.QUICK_MOVE) ? ClickType.QUICK_MOVE :
                                ClickType.PICKUP,
                Minecraft.getInstance().player);
    }

    /**
     * Looks for an item in the inventory and returns the slot id where the item is located,
     * returns -1 if the player does not have the item in their inventory
     */
    public static int getSlot(String name) {
        InventoryPlayer in = Minecraft.getInstance().player.inventory;
        for (int i = 0; i < in.mainInventory.size() + 1; i++) {
            ItemStack it = in.mainInventory.get(i);
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
        Minecraft.getInstance().playerController.windowClick(windowId, one, 0, ClickType.SWAP,
                Minecraft.getInstance().player);
        Minecraft.getInstance().playerController.windowClick(windowId, two, 0, ClickType.SWAP,
                Minecraft.getInstance().player);
        Minecraft.getInstance().playerController.tick();
    }

    /**
     * Swaps a stack between one inventory to another
     */
    @SuppressWarnings("Duplicates")
    public static void swapStack(int srcInventoryId, int dstInventoryId, int srcSlot, int dstSlot){
        Minecraft.getInstance().playerController.windowClick(srcInventoryId, srcSlot, 0,
                ClickType.SWAP, Minecraft.getInstance().player);
        Minecraft.getInstance().playerController.windowClick(dstInventoryId, dstSlot, 0,
                ClickType.SWAP, Minecraft.getInstance().player);
        Minecraft.getInstance().playerController.tick();
    }

    public static void moveStack(int srcInventoryId, int dstInventoryId, int srcSlot, int dstSlot){
        Minecraft.getInstance().playerController.windowClick(srcInventoryId, srcSlot, 0,
                ClickType.QUICK_MOVE, Minecraft.getInstance().player);
        Minecraft.getInstance().playerController.windowClick(dstInventoryId, dstSlot, 0,
                ClickType.QUICK_MOVE, Minecraft.getInstance().player);
    }

    public static void moveItem(int slotId) {
        Minecraft.getInstance().playerController.windowClick(0, slotId, 0, ClickType.QUICK_MOVE,
                Minecraft.getInstance().player);
    }

    public static void processRightClick(boolean offhand) {
        Minecraft.getInstance().playerController.processRightClick(Minecraft.getInstance().player, Minecraft.getInstance().world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
    }

    public static void resetBlockRemoving() {
        Minecraft.getInstance().playerController.resetBlockRemoving();
    }

    public static void setPlayerHittingBlock(boolean state) {
        ((IMixinPlayerControllerMP) Minecraft.getInstance().playerController).setPlayerHittingBlock(state);
    }

    public enum IClickType {
        THROW, QUICK_MOVE, PICKUP
    }

}
