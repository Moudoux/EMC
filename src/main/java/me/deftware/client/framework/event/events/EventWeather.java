package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered when weather changes
 */
public class EventWeather extends Event {

	private WeatherType type;

	public EventWeather(WeatherType type) {
		this.type = type;
	}

	public enum WeatherType {
		Rain, RainSnow
	}

}
