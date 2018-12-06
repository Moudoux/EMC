package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered when water or lava is being rendered
 * This event has been deprecated and is no longer in use,
 * @see me.deftware.client.framework.maps.SettingsMap#update(int, String, Object)
 * with key MapKeys.RENDER with and second key "FLUIDS"
 */
@Deprecated
public class EventRenderFluid extends Event {

	FluidType fluid = FluidType.WATER;

	public EventRenderFluid(FluidType fluid) {
		this.fluid = fluid;
	}

	public FluidType getFluid() {
		return fluid;
	}

	public static enum FluidType {
		WATER, LAVA
	}

}
