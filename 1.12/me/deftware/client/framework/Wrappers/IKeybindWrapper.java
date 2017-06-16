package me.deftware.client.framework.Wrappers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class IKeybindWrapper {
	
	public static boolean isPressed(IKeybind bind) {
		return bind.bind.pressed;
	}
	
	public static enum IKeybind {
		
		SNEAK(Minecraft.getMinecraft().gameSettings.keyBindSneak), USEITEM(
				Minecraft.getMinecraft().gameSettings.keyBindUseItem);

		KeyBinding bind;

		IKeybind(KeyBinding bind) {
			this.bind = bind;
		}
		
	}
	
}
