package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventNotePlayed extends Event {

	private int id;

	public EventNotePlayed(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

}
