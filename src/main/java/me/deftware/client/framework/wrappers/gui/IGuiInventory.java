package me.deftware.client.framework.wrappers.gui;

import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.client.gui.ingame.PlayerInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class IGuiInventory extends PlayerInventoryScreen {

    public IGuiInventory(IEntity player) {
        super((PlayerEntity) player.getEntity());
    }

}
