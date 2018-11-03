package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered by Minecraft living entity when checking for active potion effects
 */
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
