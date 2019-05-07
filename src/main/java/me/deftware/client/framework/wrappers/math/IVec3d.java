package me.deftware.client.framework.wrappers.math;

import me.deftware.client.framework.wrappers.world.IBlockPos;
import me.deftware.client.framework.wrappers.world.ICamera;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RayTraceContext;

public class IVec3d {

    public Vec3d vector;

    public IVec3d(double x, double y, double z) {
        vector = new Vec3d(x, y, z);
    }

    public IVec3d(Vec3d vector) {
        this.vector = vector;
    }

    public IVec3d(Vec3i vector) {
        this.vector = new Vec3d(vector);
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

    public IVec3d scale(double scale) {
        vector = vector.multiply(scale);
        return this;
    }

    public IVec3d subtract(double x, double y, double z) {
        vector = vector.subtract(x, y, z);
        return this;
    }

    public IVec3d add(double x, double y, double z) {
        vector = vector.add(x, y, z);
        return this;
    }

    public IVec3d add(IVec3d vector) {
        this.vector = this.vector.add(vector.vector);
        return this;
    }

	public static boolean rayTraceBlocks(IVec3d vec1, IVec3d vec2) {
        HitResult hitResult_1 = MinecraftClient.getInstance().world.rayTrace(new RayTraceContext(vec1.getVector(), vec2.getVector(), RayTraceContext.ShapeType.OUTLINE, RayTraceContext.FluidHandling.NONE, ICamera.getRenderViewEntity()));
        return hitResult_1.getType() != HitResult.Type.MISS;
	}

    public double squareDistanceTo(IVec3d vec) {
        return vector.distanceTo(vec.getVector());
    }

}
