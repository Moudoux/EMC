package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventNametagRender extends Event {

	private boolean isPlayer = false;

	public EventNametagRender(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public boolean isPlayer() {
		return isPlayer;
	}
}
