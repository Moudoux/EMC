package me.deftware.client.framework.math.position;

import net.minecraft.util.math.BlockPos;

/**
 * @author Deftware
 */
public class DoubleBlockPosition extends BlockPosition {

	protected double x, y, z;

	public static BlockPosition fromMinecraftBlockPos(BlockPos blockPos) {
		return new DoubleBlockPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
	}
	
	public DoubleBlockPosition(double x, double y, double z) {
		super(null);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public BlockPos getMinecraftBlockPos() {
		return new BlockPos(x, y, z);
	}


}
