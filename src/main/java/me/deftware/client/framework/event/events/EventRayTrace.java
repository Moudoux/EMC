package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.entity.IEntity;

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
