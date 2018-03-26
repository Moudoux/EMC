package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;

public class EventGuiScreenDisplay extends Event {

	private GuiScreen screen;
	private ScreenTypes type;

	public EventGuiScreenDisplay(GuiScreen screen) {
		this.screen = screen;
		if (screen instanceof GuiMainMenu) {
			type = ScreenTypes.MainMenu;
		} else if (screen instanceof GuiMultiplayer) {
			type = ScreenTypes.Multiplayer;
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
		MainMenu, Multiplayer, Unknown
	}

}
