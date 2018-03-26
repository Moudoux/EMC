package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventChatSend extends Event {

	private String message;

	public EventChatSend(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
