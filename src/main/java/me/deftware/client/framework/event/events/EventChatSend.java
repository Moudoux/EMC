package me.deftware.client.framework.event.events;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.event.Event;

/**
 * Triggered when player sends a chat message
 */
public class EventChatSend extends Event {

	private @Getter @Setter String message;
	private @Getter @Setter boolean dispatch = false;

	public EventChatSend(String message) {
		this.message = message;
	}

}
