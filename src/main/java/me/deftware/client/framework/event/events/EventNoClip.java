package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered when entity tries to move towards the specific location
 */
@Deprecated
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
