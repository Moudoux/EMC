package me.deftware.client.framework.wrappers.gui;

import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class IGuiInventory extends InventoryScreen {

    public IGuiInventory(IEntity player) {
        super((PlayerEntity) player.getEntity());
    }

}
