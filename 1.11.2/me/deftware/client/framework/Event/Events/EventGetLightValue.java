package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.IBlock;

public class EventGetLightValue extends Event {

	private IBlock block;
	private int value;

	public EventGetLightValue(IBlock block, int value) {
		this.block = block;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public IBlock getBlock() {
		return block;
	}

}
