package me.deftware.client.framework.wrappers;

import org.lwjgl.input.Keyboard;

import me.deftware.mixin.imp.IMixinKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class IKeybindWrapper {

	public static boolean isPressed(IKeybind bind) {
		return bind.bind.isKeyDown();
	}

	public static void setPressed(IKeybind bind, boolean state) {
		((IMixinKeyBinding) bind.bind).setPressed(state);
	}

	public static boolean isKeyDown(IKeybind bind) {
		return Keyboard.isKeyDown(bind.bind.getKeyCode());
	}

	public static enum IKeybind {

		SNEAK(Minecraft.getMinecraft().gameSettings.keyBindSneak), USEITEM(
				Minecraft.getMinecraft().gameSettings.keyBindUseItem), JUMP(
						Minecraft.getMinecraft().gameSettings.keyBindJump), SPRINT(
								Minecraft.getMinecraft().gameSettings.keyBindSprint), FORWARD(
										Minecraft.getMinecraft().gameSettings.keyBindForward), BACK(
												Minecraft.getMinecraft().gameSettings.keyBindBack), LEFT(
														Minecraft.getMinecraft().gameSettings.keyBindLeft), RIGHT(
																Minecraft
																		.getMinecraft().gameSettings.keyBindRight), ATTACK(
																				Minecraft
																						.getMinecraft().gameSettings.keyBindAttack);

		KeyBinding bind;

		IKeybind(KeyBinding bind) {
			this.bind = bind;
		}

	}

}
