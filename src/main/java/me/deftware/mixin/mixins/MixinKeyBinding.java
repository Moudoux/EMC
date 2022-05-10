package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinKeyBinding;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyBinding.class)
public class MixinKeyBinding implements IMixinKeyBinding {

	@Shadow
	private boolean pressed;

	@Shadow
	private InputMappings.Input keyCode;

	@Override
	public void setPressed(boolean state) {
		pressed = state;
	}

	@Override
	public InputMappings.Input getInput() {
		return keyCode;
	}


}
