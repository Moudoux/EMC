package me.deftware.client.framework.wrappers.gui.imp;

import me.deftware.client.framework.wrappers.item.ISlot;
import me.deftware.mixin.imp.IMixinGuiContainer;
import net.minecraft.client.gui.GuiScreen;

public class GuiContainerInstance extends ScreenInstance {

    public GuiContainerInstance(GuiScreen screen) {
        super(screen);
    }

    public ISlot getHoveredSlot() {
        if (((IMixinGuiContainer) screen).getHoveredSlot() == null) {
            return null;
        }
        return new ISlot(((IMixinGuiContainer) screen).getHoveredSlot());
    }

}
