package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventGetReachDistance extends Event {

	private float distance;

	public EventGetReachDistance(float distance) {
		this.distance = distance;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

}
