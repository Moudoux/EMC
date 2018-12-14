package me.deftware.client.framework.wrappers.world;


import me.deftware.client.framework.wrappers.math.IVec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

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

	public IBlockPos(IVec3d vec) {
		this.x = vec.vector.x;
		this.y = vec.vector.y;
		this.z = vec.vector.z;
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

	public static Iterable<IBlockPos> getAllInBox(IBlockPos pos1, IBlockPos pos2) {
		List<IBlockPos> newPos = new ArrayList<>();
		Iterable<BlockPos> list = BlockPos.getAllInBox(pos1.getPos(), pos2.getPos());
		list.forEach(blockPos -> newPos.add(new IBlockPos(blockPos)));
		return newPos;
	}

	public boolean isCollidable() {
		return isCollidable(this);
	}

	public static boolean isCollidable(IBlockPos pos) {
		return Minecraft.getInstance().world.getBlockState(pos.getPos()).getBlock().isCollidable(Minecraft.getInstance().world.getBlockState(pos.getPos()));
	}

}
