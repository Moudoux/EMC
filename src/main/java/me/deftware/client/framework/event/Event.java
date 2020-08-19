package me.deftware.client.framework.event;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.main.bootstrap.Bootstrap;

/**
 * This class describes the way events are defined in EMC framework and handles the process of
 * delivering events to all of the loaded mods
 *
 * @author Deftware
 */
public class Event {

    private @Getter @Setter boolean canceled = false;

    /**
     * Broadcasts an event to all registered listeners
     */
    @SuppressWarnings("unchecked")
    public <T extends Event> T broadcast() {
        try {
            EventBus.sendEvent(this);
        } catch (Exception ex) {
            Bootstrap.logger.warn("Failed to send event {}", this, ex);
        }
        return (T) this;
    }

}
