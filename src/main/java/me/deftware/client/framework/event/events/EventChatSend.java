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
	private final @Getter Class<?> sender;

	public EventChatSend(String message, Class<?> sender) {
		this.message = message;
		this.sender = sender;
	}

}
