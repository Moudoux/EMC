package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

public class EventWeather extends Event {

	private WeatherType type;

	public EventWeather(WeatherType type) {
		this.type = type;
	}

	public static enum WeatherType {
		Rain, Snow
	}

}
