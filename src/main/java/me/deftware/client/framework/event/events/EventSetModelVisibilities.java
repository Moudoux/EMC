package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered when entity (including player) becomes visible or invisible.
 * It might be when a player becomes a spectator for example
 */
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
