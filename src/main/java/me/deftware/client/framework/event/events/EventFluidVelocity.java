package me.deftware.client.framework.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.math.vector.Vector3d;

/**
 * @author Deftware
 */
public @AllArgsConstructor class EventFluidVelocity extends Event {

	private @Getter @Setter Vector3d vector3d;

}
