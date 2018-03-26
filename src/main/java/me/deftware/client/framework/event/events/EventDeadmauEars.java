package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventDeadmauEars extends Event {

	private String name;

	public EventDeadmauEars(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
