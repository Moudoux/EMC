package me.deftware.client.framework.wrappers.world;


import net.minecraft.util.math.BlockPos;

public class IBlockPos {

	private double x, y, z;
	private BlockPos pos;

	public IBlockPos(BlockPos pos) {
		this.pos = pos;
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
	}

	public IBlockPos(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		pos = new BlockPos(x, y, z);
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


	public IBlockPos add(int x, int y, int z) {
		return x == 0 && y == 0 && z == 0 ? this : new IBlockPos(getX() + x, getY() + y, getZ() + z);
	}

}
