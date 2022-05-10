package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered when player moves to determine the speed
 */
public class EventSneakingCheck extends Event {

	private boolean isSneaking;

	public EventSneakingCheck(boolean state) {
		isSneaking = state;
	}

	public boolean isSneaking() {
		return isSneaking;
	}

	public void setSneaking(boolean isSneaking) {
		this.isSneaking = isSneaking;
	}

}
