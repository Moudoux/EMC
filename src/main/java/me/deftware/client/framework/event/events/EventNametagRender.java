package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventNametagRender extends Event {

	private String name;

	public EventNametagRender(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
