package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventSlowdown extends Event {

	private SlowdownType type;

	public EventSlowdown(SlowdownType type) {
		this.type = type;
	}

	public SlowdownType getType() {
		return type;
	}

	public static enum SlowdownType {
		Soulsand, Web, Item_Use, Hunger, Blindness, Sneak
	}

}
