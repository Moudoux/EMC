package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

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
