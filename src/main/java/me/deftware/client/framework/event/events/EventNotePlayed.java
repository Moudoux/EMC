package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered when music note is played.
 *
 * Watch out! This event is deprecated and might not be available in the future
 */
@Deprecated
public class EventNotePlayed extends Event {

	private int id;

	public EventNotePlayed(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

}
