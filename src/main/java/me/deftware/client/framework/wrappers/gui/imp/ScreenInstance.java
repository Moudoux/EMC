package me.deftware.client.framework.wrappers.gui.imp;


import net.minecraft.client.gui.screen.Screen;

public class ScreenInstance {

    public Screen screen;

    public ScreenInstance(Screen screen) {
        this.screen = screen;
    }

    public void doDrawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
        screen.blit(x, y, u, v, width, height);
    }

}

