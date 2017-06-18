package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventSetFPS extends Event {

	private int fps;
	private boolean override = false, displayActive;

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

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public boolean isDisplayActive() {
		return displayActive;
	}

}
