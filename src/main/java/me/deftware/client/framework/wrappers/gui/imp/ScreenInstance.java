package me.deftware.client.framework.wrappers.gui.imp;


import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

public class ScreenInstance {

    public Screen screen;
    private final MatrixStack stack = new MatrixStack();

    public ScreenInstance(Screen screen) {
        this.screen = screen;
    }

    public void doDrawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
        screen.drawTexture(stack, x, y, u, v, width, height);
    }

}

