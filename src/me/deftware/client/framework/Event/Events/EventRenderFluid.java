package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.IBlock;

public class EventRenderFluid extends Event {

	private IBlock block;

	public EventRenderFluid(IBlock block) {
		this.block = block;
	}

	public IBlock getBlock() {
		return block;
	}

}
