package me.deftware.client.framework.event.events;

import net.minecraft.client.gui.GuiScreen;

public class EventGuiScreenPostDraw extends EventGuiScreenDraw {

    public EventGuiScreenPostDraw(GuiScreen screen, int x, int y) {
        super(screen, x, y);
    }

}
