package me.deftware.client.framework.wrappers;

import me.deftware.mixin.imp.IMixinKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;

public class IKeybindWrapper {

	public static boolean isPressed(IKeybind bind) {
		return bind.bind.isKeyDown();
	}

	public static void setPressed(IKeybind bind, boolean state) {
		((IMixinKeyBinding) bind.bind).setPressed(state);
	}

	public static boolean isKeyDown(IKeybind bind) {
		return InputMappings.isKeyDown(((IMixinKeyBinding) bind.bind).getInput().getKeyCode());
	}

	public static String getKeyName(int key) {
		return String.valueOf((char) key);
	}

	public static boolean isKeyDown(int key) {
		return InputMappings.isKeyDown(key);
	}

	public static enum IKeybind {

		SNEAK(Minecraft.getInstance().gameSettings.keyBindSneak),
		USEITEM(Minecraft.getInstance().gameSettings.keyBindUseItem),
		JUMP(Minecraft.getInstance().gameSettings.keyBindJump),
		SPRINT(Minecraft.getInstance().gameSettings.keyBindSprint),
		FORWARD(Minecraft.getInstance().gameSettings.keyBindForward),
		BACK(Minecraft.getInstance().gameSettings.keyBindBack),
		LEFT(Minecraft.getInstance().gameSettings.keyBindLeft),
		RIGHT(Minecraft.getInstance().gameSettings.keyBindRight),
		ATTACK(Minecraft.getInstance().gameSettings.keyBindAttack);

		KeyBinding bind;

		IKeybind(KeyBinding bind) {
			this.bind = bind;
		}

	}

}
