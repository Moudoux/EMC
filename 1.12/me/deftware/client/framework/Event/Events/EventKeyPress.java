package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventKeyPress extends Event {

	private int key;

	public EventKeyPress(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}
