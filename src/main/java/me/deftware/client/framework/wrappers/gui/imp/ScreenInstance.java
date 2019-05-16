package me.deftware.client.framework.wrappers.gui.imp;


import net.minecraft.client.gui.screen.Screen;

public class ScreenInstance {

    protected Screen screen;

    public ScreenInstance(Screen screen) {
        this.screen = screen;
    }

    public void doDrawTexturedModalRect(int x, int x1, int x2, int x3, int x4, int x5) {
        screen.blit(x, x1, x2, x3, x4, x5);
    }

}

