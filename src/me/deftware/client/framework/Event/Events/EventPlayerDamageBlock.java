package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;

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
