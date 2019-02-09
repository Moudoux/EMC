package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.item.IItem;

import java.util.List;

public class EventGetItemToolTip extends Event {

    private List<String> list;
    private IItem item;

    public EventGetItemToolTip(List<String> list, IItem item) {
        this.list = list;
        this.item = item;
    }

    public IItem getItem() {
        return item;
    }

    public List<String> getList() {
        return list;
    }

}

