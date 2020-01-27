package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinGuiButton;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractButtonWidget.class)
public class MixinGuiButton implements IMixinGuiButton {

    @Shadow
    protected boolean isHovered;

    @Override
    public void setIsHovered(boolean state) {
        isHovered = state;
    }

}
