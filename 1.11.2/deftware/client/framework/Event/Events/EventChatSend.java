package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

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
