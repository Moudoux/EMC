package me.deftware.client.framework.event.events;

import lombok.Getter;
import me.deftware.client.framework.entity.EntityHand;
import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.item.Item;

@Getter
public class EventItemUse extends Event {

    private final Item item;
    private final EntityHand hand;

    public EventItemUse(Item item, EntityHand hand) {
        this.item = item;
        this.hand = hand;
    }

}
