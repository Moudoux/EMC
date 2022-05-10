package me.deftware.client.framework.event;

import me.deftware.client.framework.main.Bootstrap;

/**
 * This class describes the way events are defined in EMC framework and handles the process of
 * delivering events to all of the loaded mods
 */
@SuppressWarnings("ALL")
public class Event {

    private boolean canceled = false;

    /**
     * Checks if event was canceled
     *
     * @return {@link Boolean}
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * Sets event as canceled
     */
    public <T extends Event> T setCanceled(boolean canceled) {
        this.canceled = canceled;
        return (T) this;
    }

    /**
     * Broadcasts an event to all registered listeners
     */
    public void broadcast() {
        try {
            EventBus.sendEvent(this);
        } catch (Exception ex) {
            Bootstrap.logger.warn("Failed to send event {}", this, ex);
        }
    }

}
