package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventRender2D extends Event {

	private float partialTicks;

	public EventRender2D(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

}
