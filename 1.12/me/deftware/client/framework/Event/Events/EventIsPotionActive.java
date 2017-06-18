package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventIsPotionActive extends Event {

	private boolean active;
	private String name;

	public EventIsPotionActive(String name, boolean active) {
		this.name = name;
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

}
