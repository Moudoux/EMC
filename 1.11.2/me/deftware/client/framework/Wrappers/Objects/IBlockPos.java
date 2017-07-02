package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.util.math.BlockPos;

public class IBlockPos {

	private double x, y, z;
	private BlockPos pos;

	public IBlockPos(BlockPos pos) {
		this.pos = pos;
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}

	public IBlockPos(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pos = new BlockPos(x, y, z);
	}

	public BlockPos getPos() {
		return pos;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Add the given coordinates to the coordinates of this BlockPos
	 */
	public IBlockPos add(int x, int y, int z) {
		return x == 0 && y == 0 && z == 0 ? this : new IBlockPos(this.getX() + x, this.getY() + y, this.getZ() + z);
	}

}
