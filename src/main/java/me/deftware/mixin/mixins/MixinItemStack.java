package me.deftware.mixin.mixins;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public class MixinItemStack {

	@Shadow
	private int stackSize;


}
