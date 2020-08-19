package me.deftware.client.framework.input;

import me.deftware.mixin.imp.IMixinKeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

/**
 * @author Deftware
 */
public enum MinecraftKeyBind {

	SNEAK(MinecraftClient.getInstance().options.keySneak),
	USE_ITEM(MinecraftClient.getInstance().options.keyUse),
	JUMP(MinecraftClient.getInstance().options.keyJump),
	SPRINT(MinecraftClient.getInstance().options.keySprint),
	FORWARD(MinecraftClient.getInstance().options.keyForward),
	BACK(MinecraftClient.getInstance().options.keyBack),
	LEFT(MinecraftClient.getInstance().options.keyLeft),
	RIGHT(MinecraftClient.getInstance().options.keyRight),
	ATTACK(MinecraftClient.getInstance().options.keyAttack);

	private final KeyBinding bind;

	public boolean isHeld() {
		return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), ((IMixinKeyBinding) this.bind).getInput().getCode());
	}

	public boolean isPressed() {
		return this.bind.isPressed();
	}

	public void setPressed(boolean state) {
		this.bind.setPressed(state);
	}

	MinecraftKeyBind(KeyBinding bind) {
		this.bind = bind;
	}

}
