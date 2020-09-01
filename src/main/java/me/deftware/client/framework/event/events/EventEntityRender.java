package me.deftware.client.framework.event.events;

import lombok.Getter;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.event.Event;

public class EventEntityRender extends Event {

    private @Getter final Entity entity;
    private @Getter final double x;
    private @Getter final double y;
    private @Getter final double z;

    public EventEntityRender(Entity entity, double x, double y, double z){
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
