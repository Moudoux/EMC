package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered by Minecraft gui when three-dimensional things are being rendered.
 * 
 * Be careful! This event is triggered every frame so the rate is equal to FPS 
 */
public class EventRender3D extends Event {

	private float partialTicks;

	public EventRender3D(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

}
