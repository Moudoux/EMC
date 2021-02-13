package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * @author Deftware
 */
public class EventBlockBreakingSpeed extends Event {

    private float multiplier = 1.0f;

    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

}
