package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventExtendedReach extends Event {

	private boolean state;

	public EventExtendedReach(boolean state) {
		this.state = state;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
