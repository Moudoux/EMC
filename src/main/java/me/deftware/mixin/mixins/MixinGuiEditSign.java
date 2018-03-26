package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinGuiEditSign;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiEditSign.class)
public class MixinGuiEditSign implements IMixinGuiEditSign {

	@Final
	@Shadow
	private TileEntitySign tileSign;

	@Shadow
	private int editLine;

	@Override
	public int getEditLine() {
		return editLine;
	}

	@Override
	public TileEntitySign getTileSign() {
		return tileSign;
	}

}
