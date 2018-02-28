package me.deftware.client.framework.Wrappers.Objects;

import me.deftware.client.framework.Wrappers.Entity.IEntity;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

public class IGuiInventory extends GuiInventory {

	public IGuiInventory(IEntity player) {
		super((EntityPlayer) player.getEntity());
	}

}
