package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventNametagRender extends Event {

	private boolean isPlayer = false;
	
	public EventNametagRender(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public boolean isPlayer() {
		return isPlayer;
	}
	
}
