package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.IBlock;

public class EventIsPassable extends Event {

	private IBlock block;
	private boolean passable;

	public EventIsPassable(IBlock block, boolean value) {
		this.block = block;
		this.passable = value;
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
