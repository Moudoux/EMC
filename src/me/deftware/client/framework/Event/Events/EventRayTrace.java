package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.Entity.IEntity;

public class EventRayTrace extends Event {

	private IEntity entity;

	public EventRayTrace(IEntity entity) {
		this.entity = entity;
	}

	public IEntity getEntity() {
		return entity;
	}

}
