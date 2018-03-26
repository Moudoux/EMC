package me.deftware.client.framework.wrappers.math;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class IVec3d {

	private Vec3d vector;

	public IVec3d(double x, double y, double z) {
		vector = new Vec3d(x, y, z);
	}

	public Vec3d getVector() {
		return vector;
	}

	public static boolean rayTraceBlocks(IVec3d vec1, IVec3d vec2) {
		RayTraceResult res = Minecraft.getMinecraft().world.rayTraceBlocks(vec1.getVector(), vec2.getVector());
		if (res != null) {
			return true;
		}
		return false;
	}

}
