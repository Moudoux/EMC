package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventNotePlayed extends Event {

	private int id;

	public EventNotePlayed(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

}
