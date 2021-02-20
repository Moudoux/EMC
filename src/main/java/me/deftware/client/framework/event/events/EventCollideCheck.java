package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.world.block.Block;

/**
 * Triggered by Minecraft block when checking collision
 */
public class EventCollideCheck extends Event {

	private final Block block;
	private final BlockPosition position;
	public boolean updated = false, canCollide;

	public EventCollideCheck(Block block, BlockPosition position) {
		this.block = block;
		this.position = position;
	}

	public Block getBlock() {
		return block;
	}

	public BlockPosition getPosition() {
		return position;
	}

	public void setCollidable(boolean canCollide) {
		updated = true;
		this.canCollide = canCollide;
	}

}
