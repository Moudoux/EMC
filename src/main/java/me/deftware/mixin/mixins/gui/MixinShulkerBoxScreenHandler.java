package me.deftware.mixin.mixins.gui;

import me.deftware.mixin.imp.IMixinShulkerBoxScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShulkerBoxScreenHandler.class)
public class MixinShulkerBoxScreenHandler implements IMixinShulkerBoxScreenHandler {

	@Shadow @Final private Inventory inventory;

	@Override
	public Inventory getInventory() {
		return inventory;
	}

}
