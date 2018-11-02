package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.entity.IEntity;

/**
 * Triggered by EMC freamwork when ray trace is being calculated.
 *
 * Watch out! This event is deprecated and might not be available in the future
 */
@Deprecated
// TODO
public class EventRayTrace extends Event {

	private IEntity entity;

	public EventRayTrace(IEntity entity) {
		this.entity = entity;
	}

	public IEntity getEntity() {
		return entity;
	}

}
