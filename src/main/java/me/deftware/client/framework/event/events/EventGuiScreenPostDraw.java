package me.deftware.client.framework.event.events;

import me.deftware.client.framework.gui.minecraft.ScreenInstance;

public class EventGuiScreenPostDraw extends EventGuiScreenDraw {

    public EventGuiScreenPostDraw(ScreenInstance screen, int mouseX, int mouseY) {
        super(screen, mouseX, mouseY);
    }

}

