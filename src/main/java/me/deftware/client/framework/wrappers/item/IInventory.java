package me.deftware.client.framework.wrappers.item;

import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.mixin.imp.IMixinEntityLivingBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ingame.PlayerInventoryScreen;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.server.network.packet.PlayerActionServerPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class IInventory {

    private static IEntity entity;

    public static int getFirstEmptyStack() {
        return MinecraftClient.getInstance().player.inventory.getEmptySlot();
    }

    public static int getCurrentItem() {
        return MinecraftClient.getInstance().player.inventory.selectedSlot;
    }

    public static void setCurrentItem(int id) {
        MinecraftClient.getInstance().player.inventory.selectedSlot = id;
    }

    public static int getItemInUseCount() {
        return ((IMixinEntityLivingBase) MinecraftClient.getInstance().player).getActiveItemStackUseCount();
    }

    public static void swapHands() {
        MinecraftClient.getInstance().player.networkHandler.sendPacket(new PlayerActionServerPacket(
                PlayerActionServerPacket.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, Direction.DOWN));
    }

    public static void openEntityInventory(IEntity entity) {
        IInventory.entity = entity;
    }

    public static void onRender() {
        if (IInventory.entity != null) {
            MinecraftClient.getInstance().openScreen(new PlayerInventoryScreen((OtherClientPlayerEntity) IInventory.entity.getEntity()));
            IInventory.entity = null;
        }
    }

}
