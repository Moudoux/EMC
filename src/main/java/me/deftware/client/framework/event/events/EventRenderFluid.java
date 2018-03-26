package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.world.IBlock;

public class EventRenderFluid extends Event {

	private IBlock block;

	public EventRenderFluid(IBlock block) {
		this.block = block;
	}

	public IBlock getBlock() {
		return block;
	}

}
