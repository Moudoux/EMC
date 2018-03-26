package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinItemTool;
import net.minecraft.item.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemTool.class)
public class MixinItemTool implements IMixinItemTool {

	@Shadow
	protected float damageVsEntity;

	@Override
	public float getDamageVsEntity() {
		return damageVsEntity;
	}

}
