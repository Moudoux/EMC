package me.deftware.client.framework.wrappers.gui.imp;

import me.deftware.client.framework.wrappers.item.ISlot;
import me.deftware.mixin.imp.IMixinGuiContainer;
import net.minecraft.client.gui.Screen;

public class GuiContainerInstance extends ScreenInstance {

    public GuiContainerInstance(Screen screen) {
        super(screen);
    }

    public ISlot getHoveredSlot() {
        if (((IMixinGuiContainer) screen).getHoveredSlot() == null) {
            return null;
        }
        return new ISlot(((IMixinGuiContainer) screen).getHoveredSlot());
    }

}

