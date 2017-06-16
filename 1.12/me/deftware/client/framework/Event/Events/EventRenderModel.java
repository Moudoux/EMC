package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.IBlock;

public class EventRenderModel extends Event {

	private IBlock block;

	public EventRenderModel(IBlock block) {
		this.block = block;
	}

	public IBlock getBlock() {
		return block;
	}

}
