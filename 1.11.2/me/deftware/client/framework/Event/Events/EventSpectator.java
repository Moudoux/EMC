package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventSpectator extends Event {

	private boolean spectator;

	public EventSpectator(boolean spectator) {
		this.spectator = spectator;
	}

	public boolean isSpectator() {
		return spectator;
	}

	public void setSpectator(boolean spectator) {
		this.spectator = spectator;
	}

}
