package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered when player is slowed down by hunger, webs, sneaking etc.
 */
public class EventSlowdown extends Event {

	private SlowdownType type;
	private float multiplier = 1f;

	public EventSlowdown(SlowdownType type, float multiplier) {
		this.type = type;
		this.multiplier = multiplier;
	}

	public EventSlowdown(SlowdownType type) {
		this.type = type;
	}

	public SlowdownType getType() {
		return type;
	}

	public float getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(float value) {
		multiplier = value;
	}

	public enum SlowdownType {
		BerryBush, Soulsand, Web, Item_Use, Hunger, Blindness, Sneak, Honey, Slipperiness
	}

}
