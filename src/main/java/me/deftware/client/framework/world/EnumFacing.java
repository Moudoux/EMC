package me.deftware.client.framework.world;

import me.deftware.client.framework.math.vector.Vector3d;
import net.minecraft.util.math.Direction;

/**
 * @author Deftware
 */
public enum EnumFacing {

	DOWN(Direction.DOWN), UP(Direction.UP), NORTH(Direction.NORTH), SOUTH(Direction.SOUTH), WEST(Direction.WEST), EAST(Direction.EAST);

	private final Direction direction;
	private final Vector3d vector3d;

	EnumFacing(Direction direction) {
		this.direction = direction;
		this.vector3d = new Vector3d(direction.getVector());
	}

	public Vector3d getVector3d() {
		return vector3d;
	}

	public Direction getFacing() {
		return direction;
	}

}
