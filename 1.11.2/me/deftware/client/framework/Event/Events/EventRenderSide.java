package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.IBlock;

public class EventRenderSide extends Event {

	private IBlock block;
	private boolean override = false;

	public EventRenderSide(IBlock block) {
		this.block = block;
	}

	public IBlock getBlock() {
		return block;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

}
