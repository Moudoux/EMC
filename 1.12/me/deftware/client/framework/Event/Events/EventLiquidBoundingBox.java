package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;

public class EventLiquidBoundingBox extends Event {

	private Type type;

	public EventLiquidBoundingBox() {
		type = Type.NULL_AABB;
	}

	public AxisAlignedBB getAABB() {
		if (type.equals(Type.NULL_AABB)) {
			return Block.NULL_AABB;
		}
		return Block.FULL_BLOCK_AABB;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public static enum Type {
		NULL_AABB, FULL_BLOCK_AABB
	}

}
