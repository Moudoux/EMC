package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventPlayerDamageBlock extends Event {

	private float curBlockDamageMP;

	public EventPlayerDamageBlock(float curBlockDamageMP) {
		this.curBlockDamageMP = curBlockDamageMP;
	}

	public float getCurBlockDamageMP() {
		return curBlockDamageMP;
	}

	public void setCurBlockDamageMP(float curBlockDamageMP) {
		this.curBlockDamageMP = curBlockDamageMP;
	}

}
