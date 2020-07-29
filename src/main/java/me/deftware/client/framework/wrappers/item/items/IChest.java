package me.deftware.client.framework.wrappers.item.items;

import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.GenericContainerScreenHandler;

public class IChest {

    private static GenericContainerScreenHandler getCurrent() {
        if (MinecraftClient.getInstance().player.currentScreenHandler == null) {
            return null;
        }
        return (GenericContainerScreenHandler) MinecraftClient.getInstance().player.currentScreenHandler;
    }

    public static int getInventorySize() {
        if (IChest.getCurrent() == null) {
            return 0;
        }
        return IChest.getCurrent().getInventory().size();
    }

    public static IItemStack getStackInSlot(int id) {
        return new IItemStack(IChest.getCurrent().getInventory().getStack(id));
    }

    public static int getContainerID() {
        return IChest.getCurrent().syncId;
    }

    public static boolean isEmpty() {
        return IChest.getCurrent().getInventory().isEmpty();
    }

}
