package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

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
