package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventRenderPlayerModel extends Event {

	private boolean shouldRender;

	public EventRenderPlayerModel(boolean value) {
		this.shouldRender = value;
	}

	public boolean isShouldRender() {
		return shouldRender;
	}

	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
	}

}
