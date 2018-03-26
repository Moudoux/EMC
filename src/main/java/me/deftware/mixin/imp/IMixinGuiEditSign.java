package me.deftware.mixin.imp;

import net.minecraft.tileentity.TileEntitySign;

public interface IMixinGuiEditSign {

	int getEditLine();

	TileEntitySign getTileSign();

}
