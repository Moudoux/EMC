package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventRenderPlayerModel extends Event {

	private boolean shouldRender = false;

	public EventRenderPlayerModel() {

	}

	public boolean isShouldRender() {
		return shouldRender;
	}

	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
	}

}
