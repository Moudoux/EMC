package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered when player sends a chat message and it starts with "#" symbol.
 * This feature had been added by EMC freamwork to make it easier to connect mods with IRC servers
 */
public class EventIRCMessage extends Event {

	private String message;

	public EventIRCMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
