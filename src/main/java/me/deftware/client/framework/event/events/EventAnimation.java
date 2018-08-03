package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventAnimation extends Event {

	private AnimationType type;

	public EventAnimation(AnimationType type) {
		this.type = type;
	}

	public AnimationType getAnimationType() {
		return type;
	}

	public enum AnimationType {
		Totem
	}

}
