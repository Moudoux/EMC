package me.deftware.client.framework.event.events;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.event.Event;

/**
 * Triggered by EMC framework when ray trace is being calculated.
 * <p>
 * Watch out! This event is deprecated and might not be available in the future
 */
@Deprecated
public class EventRayTrace extends Event {

	private Entity entity;

	public EventRayTrace(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

}
