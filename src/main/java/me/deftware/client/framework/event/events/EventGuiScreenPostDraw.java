package me.deftware.client.framework.event.events;

import net.minecraft.client.gui.Screen;

public class EventGuiScreenPostDraw extends EventGuiScreenDraw {

    public EventGuiScreenPostDraw(Screen screen, int x, int y) {
        super(screen, x, y);
    }

}

