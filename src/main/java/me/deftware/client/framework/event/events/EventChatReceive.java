package me.deftware.client.framework.event.events;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.Event;

/**
 * Triggered by Minecraft chat listener at the moment the message is drawn to screen
 */
public class EventChatReceive extends Event {

    private @Getter @Setter ChatMessage message;

    public EventChatReceive(ChatMessage message) {
        this.message = message;
    }

}
