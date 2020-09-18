package me.deftware.mixin.imp;

import net.minecraft.client.util.InputUtil;

public interface IMixinKeyBinding {

    void emcSetPressed(boolean state);

    InputUtil.Key getInput();

}
