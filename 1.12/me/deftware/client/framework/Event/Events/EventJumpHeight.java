package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.AsyncEvent;

public class EventJumpHeight extends AsyncEvent {

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
