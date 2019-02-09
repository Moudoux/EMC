package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinGuiContainer;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.container.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ContainerScreen.class)
public class MixinGuiContainer implements IMixinGuiContainer {

    @Shadow
    private Slot focusedSlot;

    @Override
    public Slot getHoveredSlot() {
        return focusedSlot;
    }


}
