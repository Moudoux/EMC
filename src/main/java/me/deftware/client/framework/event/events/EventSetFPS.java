package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventSetFPS extends Event {

	private int fps;
	private boolean displayActive;

	public EventSetFPS(int fps, boolean displayActive) {
		this.fps = fps;
		this.displayActive = displayActive;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public boolean isDisplayActive() {
		return displayActive;
	}

}
