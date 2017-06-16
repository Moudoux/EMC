package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

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
