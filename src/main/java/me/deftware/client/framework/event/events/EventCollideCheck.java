package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.world.IBlock;

/**
 * Triggered by Minecraft block when checking collision
 */
public class EventCollideCheck extends Event {

	private IBlock block;
	private boolean canCollide;

	public EventCollideCheck(IBlock block, boolean canCollide) {
		this.block = block;
		this.canCollide = canCollide;
	}

	public IBlock getBlock() {
		return block;
	}

	public void setBlock(IBlock block) {
		this.block = block;
	}

	public boolean isCollidable() {
		return canCollide;
	}

	public void setisCollidable(boolean canCollide) {
		this.canCollide = canCollide;
	}

}
