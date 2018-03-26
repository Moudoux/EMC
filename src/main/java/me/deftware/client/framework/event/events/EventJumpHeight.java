package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventJumpHeight extends Event {

	private float height;

	public EventJumpHeight(float height) {
		this.height = height;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

}
