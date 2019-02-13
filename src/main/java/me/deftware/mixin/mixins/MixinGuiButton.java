package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinGuiButton;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ButtonWidget.class)
public class MixinGuiButton implements IMixinGuiButton {

    @Shadow
    private boolean field_2075;

    @Override
    public void setIsHovered(boolean state) {
        field_2075 = state;
    }

}
