package me.deftware.client.framework.input;

import me.deftware.mixin.imp.IMixinKeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

/**
 * @author Deftware
 */
public enum MinecraftKeyBind {

	SNEAK(MinecraftClient.getInstance().options.sneakKey),
	USE_ITEM(MinecraftClient.getInstance().options.useKey),
	JUMP(MinecraftClient.getInstance().options.jumpKey),
	SPRINT(MinecraftClient.getInstance().options.sprintKey),
	FORWARD(MinecraftClient.getInstance().options.forwardKey),
	BACK(MinecraftClient.getInstance().options.backKey),
	LEFT(MinecraftClient.getInstance().options.leftKey),
	RIGHT(MinecraftClient.getInstance().options.rightKey),
	ATTACK(MinecraftClient.getInstance().options.attackKey);

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
