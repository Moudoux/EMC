package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinKeyBinding;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyBinding.class)
public class MixinKeyBinding implements IMixinKeyBinding {

    @Shadow
    private boolean pressed;

    @Shadow
    private InputUtil.KeyCode keyCode;

    @Override
    public void setPressed(boolean state) {
        pressed = state;
    }

    @Override
    public InputUtil.KeyCode getInput() {
        return keyCode;
    }


}
