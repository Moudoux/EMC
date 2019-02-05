package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinGuiContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiContainer.class)
public class MixinGuiContainer implements IMixinGuiContainer {

    @Shadow
    private Slot hoveredSlot;

    @Override
    public Slot getHoveredSlot() {
        return hoveredSlot;
    }

}
