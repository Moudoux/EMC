package me.deftware.client.framework.wrappers.gui.imp;


import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

public class ScreenInstance {

    public Screen screen;

    public ScreenInstance(Screen screen) {
        this.screen = screen;
    }

    public void doDrawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
        doDrawTexturedModalRect(new MatrixStack(), x, y, u, v, width, height);
    }

    public void doDrawTexturedModalRect(MatrixStack matrixStack, int x, int y, int u, int v, int width, int height) {
        screen.drawTexture(matrixStack, x, y, u, v, width, height);
    }

}

