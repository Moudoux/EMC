package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinKeyBinding;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyBinding.class)
public class MixinKeyBinding implements IMixinKeyBinding {

	@Shadow
	private boolean pressed;

	@Override
	public void setPressed(boolean state) {
		pressed = state;
	}

}
