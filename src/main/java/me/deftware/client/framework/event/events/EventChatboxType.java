package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventChatboxType extends Event {

	private String text, overlay;

	public EventChatboxType(String text, String overlay) {
		this.text = text;
		this.overlay = overlay;
	}

	public String getOverlay() {
		return overlay;
	}

	public void setOverlay(String overlay) {
		this.overlay = overlay;
	}

	public String getText() {
		return text;
	}

}
