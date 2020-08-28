package me.deftware.mixin.mixins.gui;

import me.deftware.mixin.imp.IMixinGuiButton;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractButtonWidget.class)
public class MixinGuiButton implements IMixinGuiButton {

    @Shadow
    protected boolean hovered;

    @Override
    public void setIsHovered(boolean state) {
        hovered = state;
    }

}
