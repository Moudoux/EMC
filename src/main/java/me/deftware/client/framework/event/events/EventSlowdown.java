package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered when player is slowed down by hunger, webs, sneaking etc.
 */
public class EventSlowdown extends Event {

	private SlowdownType type;

	public EventSlowdown(SlowdownType type) {
		this.type = type;
	}

	public SlowdownType getType() {
		return type;
	}

	public enum SlowdownType {
		BerryBush, Soulsand, Web, Item_Use, Hunger, Blindness, Sneak, Honey, Slipperiness
	}

}
