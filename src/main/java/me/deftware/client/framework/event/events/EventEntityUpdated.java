package me.deftware.client.framework.event.events;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.event.Event;

/**
 * @author Deftware
 */
public class EventEntityUpdated extends Event {

    private final Change change;
    private final Entity entity;

    public EventEntityUpdated(Change change, Entity entity) {
        this.change = change;
        this.entity = entity;
    }

    public Change getChange() {
        return change;
    }

    public Entity getEntity() {
        return entity;
    }

    public enum Change {
        Added, Removed
    }

}
