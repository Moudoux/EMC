package me.deftware.client.framework.wrappers.gui;

import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

public class IGuiInventory extends GuiInventory {

	public IGuiInventory(IEntity player) {
		super((EntityPlayer) player.getEntity());
	}

}
