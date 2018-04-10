package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventRender2D extends Event {

	private float partialTicks;

	public EventRender2D(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

}
