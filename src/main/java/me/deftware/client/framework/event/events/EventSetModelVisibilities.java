package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

@Deprecated
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
