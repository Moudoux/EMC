package me.deftware.client.framework.wrappers.item.items;

import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.container.GenericContainer;

public class IChest {

    private static GenericContainer.Generic9x3 getCurrent() {
        if (MinecraftClient.getInstance().player.container == null) {
            return null;
        }
        return (GenericContainer.Generic9x3) MinecraftClient.getInstance().player.container;
    }

    public static int getInventorySize() {
        if (IChest.getCurrent() == null) {
            return 0;
        }
        return IChest.getCurrent().getInventory().getInvSize();
    }

    public static IItemStack getStackInSlot(int id) {
        return new IItemStack(IChest.getCurrent().getInventory().getInvStack(id));
    }

    public static int getContainerID() {
        return IChest.getCurrent().syncId;
    }

    public static boolean isEmpty() {
        return IChest.getCurrent().getInventory().isInvEmpty();
    }

}
