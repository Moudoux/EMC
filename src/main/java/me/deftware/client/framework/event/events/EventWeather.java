package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventWeather extends Event {

	private WeatherType type;

	public EventWeather(WeatherType type) {
		this.type = type;
	}

	public enum WeatherType {
		Rain, RainSnow
	}

}
