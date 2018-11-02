package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered by Minecraft gui when two-dimensional things are being rendered.
 * 
 * Be careful! This event is triggered every frame so the rate is equal to FPS
 */
public class EventRender2D extends Event {

	private float partialTicks;

	public EventRender2D(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

}
