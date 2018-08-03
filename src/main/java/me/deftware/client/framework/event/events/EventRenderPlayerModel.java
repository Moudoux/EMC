package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import net.minecraft.entity.Entity;

public class EventRenderPlayerModel extends Event {

	private boolean shouldRender = false;
	private Entity entity;

	public EventRenderPlayerModel(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public boolean isShouldRender() {
		return shouldRender;
	}

	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
	}

}
