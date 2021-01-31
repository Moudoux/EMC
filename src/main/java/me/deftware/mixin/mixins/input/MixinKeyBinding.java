package me.deftware.mixin.mixins.input;

import me.deftware.mixin.imp.IMixinKeyBinding;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyBinding.class)
public class MixinKeyBinding implements IMixinKeyBinding {

    @Shadow
    private boolean pressed;

    @Shadow
    private InputUtil.Key boundKey;

    @Override
    public void emcSetPressed(boolean state) {
        pressed = state;
    }

    @Override
    public InputUtil.Key getInput() {
        return boundKey;
    }


}
