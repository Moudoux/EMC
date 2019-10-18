package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.world.IBlock;

/**
 * Triggered by Minecraft block when checking collision
 */
public class EventCollideCheck extends Event {

	private IBlock block;
	public boolean updated = false, canCollide;

	public EventCollideCheck(IBlock block) {
		this.block = block;
	}

	public IBlock getBlock() {
		return block;
	}

	public void setisCollidable(boolean canCollide) {
		updated = true;
		this.canCollide = canCollide;
	}

}
