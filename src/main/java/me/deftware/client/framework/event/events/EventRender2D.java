package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered by Minecraft gui when two-dimensional things are being rendered.
 * <p>
 * Be careful! This event is triggered every frame so the rate is equal to FPS
 */
public class EventRender2D extends Event {

	protected float partialTicks;

	public float getPartialTicks() {
		return partialTicks;
	}

	public EventRender2D create(float partialTicks) {
		this.setCanceled(false);
		this.partialTicks = partialTicks;
		return this;
	}

}
