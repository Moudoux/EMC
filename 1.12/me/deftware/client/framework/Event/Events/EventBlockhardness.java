package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventBlockhardness extends Event {

	private float multiplier;

	public EventBlockhardness() {
		this.multiplier = 1;
	}

	public float getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

}
