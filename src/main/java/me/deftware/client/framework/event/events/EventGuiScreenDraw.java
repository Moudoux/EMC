package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.gui.minecraft.ScreenInstance;
import me.deftware.client.framework.gui.widgets.Button;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

import java.util.List;

/**
 * Triggered every time a gui is drawn on screen.
 * This event also contains a list of buttons that will be drawn in that particular gui
 */
public class EventGuiScreenDraw extends Event {

    private final ScreenInstance screen;
    private final int mouseX, mouseY;

    public EventGuiScreenDraw(ScreenInstance screen, int mouseX, int mouseY) {
        this.screen = screen;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public ScreenInstance getInstance() {
        return screen;
    }

    public void addButton(Button button) {
        ((IMixinGuiScreen) screen.getMinecraftScreen()).getButtonList().add(button);
        ((IMixinGuiScreen) screen.getMinecraftScreen()).getEventList().add(button);
    }

    public List<Button> getIButtonList() {
        return ((IMixinGuiScreen) screen).getEmcButtons();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getWidth() {
        return screen.getMinecraftScreen().width;
    }

    public int getHeight() {
        return screen.getMinecraftScreen().height;
    }

}

