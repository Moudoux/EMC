package me.deftware.client.framework.math.position;

import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.math.box.DoubleBoundingBox;
import me.deftware.client.framework.math.vector.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/**
 * @author Deftware
 */
public class BlockPosition {

	private static final int
			SIZE_BITS_X = 1 + MathHelper.log2(MathHelper.smallestEncompassingPowerOfTwo(30000000)),
			SIZE_BITS_Z = SIZE_BITS_X,
			SIZE_BITS_Y = 64 - SIZE_BITS_X - SIZE_BITS_Z;
	private static final int
			BIT_SHIFT_Z = SIZE_BITS_Y,
			BIT_SHIFT_X = SIZE_BITS_Y + SIZE_BITS_Z;

	protected final Entity entity;

	public BlockPosition(Entity entity) {
		this.entity = entity;
	}

	public double getX() {
		return entity.getX();
	}

	public double getY() {
		return entity.getY();
	}

	public double getZ() {
		return entity.getZ();
	}

	public BlockPos getMinecraftBlockPos() {
		return entity.getBlockPos();
	}

	public BoundingBox getBoundingBox() {
		return new DoubleBoundingBox(getX(), getY(), getZ(), getX() + 1.0, getY() + 1.0, getZ() + 1.0);
	}

	public BlockPosition offset(int x, int y, int z) {
		return new DoubleBlockPosition(getX() + x, getY() + y, getZ() + z);
	}

	public Vector3d getVector() {
		return new Vector3d(getX(), getY(), getZ());
	}

	public float distanceTo(BlockPosition position) {
		float deltaX = (float) (getX() - position.getX());
		float deltaY = (float) (getY() - position.getY());
		float deltaZ = (float) (getZ() - position.getZ());
		return MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
	}

	public static int unpackLongX(long x) {
		return (int)(x << 64 - BIT_SHIFT_X - SIZE_BITS_X >> 64 - SIZE_BITS_X);
	}

	public static int unpackLongY(long y) {
		return (int)(y << 64 - SIZE_BITS_Y >> 64 - SIZE_BITS_Y);
	}

	public static int unpackLongZ(long z) {
		return (int)(z << 64 - BIT_SHIFT_Z - SIZE_BITS_Z >> 64 - SIZE_BITS_Z);
	}

	@Override
	public String toString() {
		return getX() + ", " + getY() + ", " + getZ();
	}

}
