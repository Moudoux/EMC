package me.deftware.client.framework.event.events;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.event.Event;

public class EventCameraClip extends Event {

    private @Setter @Getter double distance;

    public EventCameraClip create(double desiredDistance) {
        setCanceled(false);
        this.distance = desiredDistance;
        return this;
    }

}
