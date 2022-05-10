package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered by Minecraft abstract client when it gets field of view modifier.
 * It can get executed when player is flying for example
 */
public class EventFovModifier extends Event {

	private float fov;

	public EventFovModifier(float fov) {
		this.fov = fov;
	}

	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}

}
