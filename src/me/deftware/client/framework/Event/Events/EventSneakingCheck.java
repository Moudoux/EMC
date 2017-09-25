package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventSneakingCheck extends Event {

	private boolean isSneaking;

	public EventSneakingCheck(boolean state) {
		this.isSneaking = state;
	}

	public boolean isSneaking() {
		return isSneaking;
	}

	public void setSneaking(boolean isSneaking) {
		this.isSneaking = isSneaking;
	}

}
