package me.deftware.client.framework.wrappers.world;


import me.deftware.client.framework.wrappers.math.IVec3d;
import net.minecraft.util.EnumFacing;

public enum IEnumFacing {

	DOWN, UP, NORTH, SOUTH, WEST, EAST;

	public static IVec3d getDirectionVec(IEnumFacing facing) {
		return new IVec3d(getFacing(facing).getDirectionVec());
	}

	public static EnumFacing getFacing(IEnumFacing facing) {
		if (facing.equals(IEnumFacing.DOWN)) {
			return EnumFacing.DOWN;
		} else if (facing.equals(IEnumFacing.UP)) {
			return EnumFacing.UP;
		} else if (facing.equals(IEnumFacing.NORTH)) {
			return EnumFacing.NORTH;
		} else if (facing.equals(IEnumFacing.SOUTH)) {
			return EnumFacing.SOUTH;
		} else if (facing.equals(IEnumFacing.WEST)) {
			return EnumFacing.WEST;
		} else if (facing.equals(IEnumFacing.EAST)) {
			return EnumFacing.EAST;
		}
		return null;
	}

}
