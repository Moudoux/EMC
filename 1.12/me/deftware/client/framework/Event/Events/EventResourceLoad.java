package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventResourceLoad extends Event {

	private String texture;

	public EventResourceLoad(String texture) {
		this.texture = texture;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

}
