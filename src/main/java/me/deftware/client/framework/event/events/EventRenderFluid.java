package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.world.IBlock;

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
