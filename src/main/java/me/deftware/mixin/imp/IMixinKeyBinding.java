package me.deftware.mixin.imp;

import net.minecraft.client.util.InputMappings;

public interface IMixinKeyBinding {

	void setPressed(boolean state);

	InputMappings.Input getInput();

}
