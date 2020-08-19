package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.world.block.Block;

/**
 * Triggered by Minecraft block when checking collision
 */
public class EventCollideCheck extends Event {

	private final Block block;
	public boolean updated = false, canCollide;

	public EventCollideCheck(Block block) {
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}

	public void setCollidable(boolean canCollide) {
		updated = true;
		this.canCollide = canCollide;
	}

}
