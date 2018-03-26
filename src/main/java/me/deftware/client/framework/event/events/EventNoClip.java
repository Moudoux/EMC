package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventNoClip extends Event {

	private boolean noclip;

	public EventNoClip(boolean noclip) {
		this.noclip = noclip;
	}

	public boolean isNoclip() {
		return noclip;
	}

	public void setNoclip(boolean noclip) {
		this.noclip = noclip;
	}

}
