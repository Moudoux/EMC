package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered by Minecraft abstract horse when checking for saddle
 */
public class EventSaddleCheck extends Event {

	private boolean state;

	public EventSaddleCheck(boolean state) {
		this.state = state;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
