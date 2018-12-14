package me.deftware.client.framework.wrappers.math;

import me.deftware.client.framework.wrappers.world.IBlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class IVec3d {

	public Vec3d vector;

	public IVec3d(double x, double y, double z) {
		vector = new Vec3d(x, y, z);
	}

	public IVec3d(Vec3d vector) {
		this.vector = vector;
	}

	public IVec3d(IBlockPos pos) {
		vector = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
	}

	public Vec3d getVector() {
		return vector;
	}

	public double getX() {
		return vector.x;
	}

	public double getY() {
		return vector.y;
	}

	public double getZ() {
		return vector.z;
	}

	public IVec3d subtract(double x, double y, double z) {
		this.vector.subtract(x, y, z);
		return this;
	}

	public IVec3d add(double x, double y, double z) {
		this.vector.add(x, y, z);
		return this;
	}

	public static boolean rayTraceBlocks(IVec3d vec1, IVec3d vec2) {
		RayTraceResult res = Minecraft.getInstance().world.rayTraceBlocks(vec1.getVector(), vec2.getVector());
		if (res != null) {
			return true;
		}
		return false;
	}

	public double squareDistanceTo(IVec3d vec) {
		return vector.squareDistanceTo(vec.getVector());
	}

}
