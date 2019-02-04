package me.deftware.client.framework.wrappers.gui.imp;

import net.minecraft.client.gui.GuiScreen;

public class ScreenInstance {

    protected GuiScreen screen;

    public ScreenInstance(GuiScreen screen) {
        this.screen = screen;
    }

    public void doDrawTexturedModalRect(int x, int x1, int x2, int x3, int x4, int x5) {
        screen.drawTexturedModalRect(x, x1, x2, x3, x4, x5);
    }

}
