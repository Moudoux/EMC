package me.deftware.client.framework.event.events;

import lombok.Getter;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.event.Event;

public class EventEntityRender extends Event {

    private @Getter Entity entity;
    private @Getter double x;
    private @Getter double y;
    private @Getter double z;

    public EventEntityRender create(Entity entity, double x, double y, double z) {
        setCanceled(false);
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

}
