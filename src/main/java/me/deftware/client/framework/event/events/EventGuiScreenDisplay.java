package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;

/**
 * Triggered every time a game gui is displayed
 */
public class EventGuiScreenDisplay extends Event {

	private GuiScreen screen;
	private ScreenTypes type;

	public EventGuiScreenDisplay(GuiScreen screen) {
		this.screen = screen;
		if (screen instanceof GuiMainMenu) {
			type = ScreenTypes.MainMenu;
		} else if (screen instanceof GuiMultiplayer) {
			type = ScreenTypes.Multiplayer;
		} else if (screen instanceof GuiIngameMenu) {
			type = ScreenTypes.GuiIngameMenu;
		} else {
			type = ScreenTypes.Unknown;
		}
	}

	public GuiScreen getScreen() {
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
