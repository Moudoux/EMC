package me.deftware.client.framework.wrappers.world;


import me.deftware.client.framework.wrappers.math.IVec3d;
import net.minecraft.util.math.Direction;

public enum IEnumFacing {

    DOWN, UP, NORTH, SOUTH, WEST, EAST;

    public static IVec3d getDirectionVec(IEnumFacing facing) {
        return new IVec3d(getFacing(facing).getVector());
    }

    public static Direction getFacing(IEnumFacing facing) {
        if (facing.equals(IEnumFacing.DOWN)) {
            return Direction.DOWN;
        } else if (facing.equals(IEnumFacing.UP)) {
            return Direction.UP;
        } else if (facing.equals(IEnumFacing.NORTH)) {
            return Direction.NORTH;
        } else if (facing.equals(IEnumFacing.SOUTH)) {
            return Direction.SOUTH;
        } else if (facing.equals(IEnumFacing.WEST)) {
            return Direction.WEST;
        } else if (facing.equals(IEnumFacing.EAST)) {
            return Direction.EAST;
        }
        return null;
    }

}
