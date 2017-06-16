package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;

public class EventGuiScreenDraw extends Event {

	private GuiScreen screen;

	public EventGuiScreenDraw(GuiScreen screen) {
		this.screen = screen;
	}

	public boolean instanceOf(CommonScreenTypes type) {
		if (type.equals(CommonScreenTypes.GuiDisconnected)) {
			return screen instanceof GuiDisconnected;
		}
		return false;
	}

	public static enum CommonScreenTypes {
		GuiDisconnected
	}

}
