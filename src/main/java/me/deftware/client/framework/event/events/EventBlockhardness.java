package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventBlockhardness extends Event {

	private float multiplier;

	public EventBlockhardness() {
		multiplier = 1;
	}

	public float getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

}
