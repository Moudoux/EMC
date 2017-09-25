package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventSetModelVisibilities extends Event {

	private boolean spectator;

	public EventSetModelVisibilities(boolean spectator) {
		this.spectator = spectator;
	}

	public boolean isSpectator() {
		return spectator;
	}

	public void setSpectator(boolean spectator) {
		this.spectator = spectator;
	}

}
