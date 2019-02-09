package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import net.minecraft.client.gui.MainMenuScreen;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.menu.MultiplayerScreen;
import net.minecraft.client.gui.menu.PauseMenuScreen;

/**
 * Triggered every time a game gui is displayed
 */
public class EventGuiScreenDisplay extends Event {

    private Screen screen;
    private ScreenTypes type;

    public EventGuiScreenDisplay(Screen screen) {
        this.screen = screen;
        if (screen instanceof MainMenuScreen) {
            type = ScreenTypes.MainMenu;
        } else if (screen instanceof MultiplayerScreen) {
            type = ScreenTypes.Multiplayer;
        } else if (screen instanceof PauseMenuScreen) {
            type = ScreenTypes.GuiIngameMenu;
        } else {
            type = ScreenTypes.Unknown;
        }
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(IGuiScreen screen) {
        this.screen = screen;
    }

    public ScreenTypes getType() {
        return type;
    }

    public static enum ScreenTypes {
        MainMenu, Multiplayer, GuiIngameMenu, Unknown
    }

}
