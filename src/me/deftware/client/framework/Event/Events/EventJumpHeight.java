package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

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
