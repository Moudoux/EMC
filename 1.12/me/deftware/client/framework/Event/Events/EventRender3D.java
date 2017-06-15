package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventRender3D extends Event {

	private float partialTicks;

	public EventRender3D(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

}
