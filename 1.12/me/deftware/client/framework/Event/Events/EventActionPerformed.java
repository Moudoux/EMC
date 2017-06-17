package me.deftware.client.framework.Event.Events;

import net.minecraft.client.gui.GuiScreen;

public class EventActionPerformed extends EventGuiScreenDraw {

	private int id;

	public EventActionPerformed(GuiScreen screen, int id) {
		super(screen);
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
