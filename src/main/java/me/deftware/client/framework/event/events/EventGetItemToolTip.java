package me.deftware.client.framework.event.events;

import lombok.Getter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.item.Item;

import java.util.List;

public class EventGetItemToolTip extends Event {

    private final @Getter List<ChatMessage> list;
    private final @Getter Item item;

    public EventGetItemToolTip(List<ChatMessage> list, Item item) {
        this.list = list;
        this.item = item;
    }

}

