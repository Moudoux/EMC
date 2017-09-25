package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

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
