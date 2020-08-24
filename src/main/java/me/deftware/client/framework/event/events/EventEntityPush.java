package me.deftware.client.framework.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.event.Event;

/**
 * @author Deftware
 */
public @AllArgsConstructor class EventEntityPush extends Event {

	private final @Getter Entity entity;

}
