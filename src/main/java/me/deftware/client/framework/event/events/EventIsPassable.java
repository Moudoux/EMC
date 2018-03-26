package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.world.IBlock;

public class EventIsPassable extends Event {

	private IBlock block;
	private boolean passable;

	public EventIsPassable(IBlock block, boolean value) {
		this.block = block;
		passable = value;
	}

	public IBlock getBlock() {
		return block;
	}

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

}
