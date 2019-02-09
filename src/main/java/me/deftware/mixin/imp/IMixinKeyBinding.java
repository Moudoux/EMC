package me.deftware.mixin.imp;

import net.minecraft.client.util.InputUtil;

public interface IMixinKeyBinding {

    void setPressed(boolean state);

    InputUtil.KeyCode getInput();

}
