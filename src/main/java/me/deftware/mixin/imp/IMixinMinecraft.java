package me.deftware.mixin.imp;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.Window;

public interface IMixinMinecraft {

    Session getSession();

    void setSession(Session session);

    RenderTickCounter getTimer();

    Window getMainWindow();

    void displayGuiScreen(Screen guiScreenIn);

    void doRightClickMouse();

    void doClickMouse();

    void doMiddleClickMouse();

    void setRightClickDelayTimer(int delay);

    boolean getIsWindowFocused();

    int getFPS();

}
