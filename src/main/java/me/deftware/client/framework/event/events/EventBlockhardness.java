package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Get the hardness of the Block relative to the ability of the given player.
 * This includes player digging speed, potion effects etc.
 */
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
