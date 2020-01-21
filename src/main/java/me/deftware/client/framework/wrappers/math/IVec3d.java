package me.deftware.client.framework.wrappers.math;

import me.deftware.client.framework.wrappers.world.IBlockPos;
import me.deftware.client.framework.wrappers.world.ICamera;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RayTraceContext;

public class IVec3d {

    public Vec3d vector, fluentVector;

    public IVec3d(double x, double y, double z) {
        vector = new Vec3d(x, y, z);
        fluentVector = vector;
    }

    public IVec3d(Vec3d vector) {
        this.vector = vector;
        this.fluentVector = vector;
    }

    public IVec3d(Vec3i vector) {
        this.vector = new Vec3d(vector);
        this.fluentVector = this.vector;
    }

    public IVec3d(IBlockPos pos) {
        vector = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        fluentVector = vector;
    }

    public Vec3d getVector() {
        return vector;
    }

    public Vec3d getFluentVector() {
        return fluentVector;
    }

    public double getX() {
        return vector.x;
    }

    public double getFluentX() {
        return fluentVector.x;
    }

    public double getY() {
        return vector.y;
    }

    public double getFluentY() {
        return fluentVector.y;
    }

    public double getZ() {
        return vector.z;
    }

    public double getFluentZ() {
        return fluentVector.z;
    }

    public IVec3d scale(double scale) {
        vector = vector.multiply(scale);
        return this;
    }

    public IVec3d subtract(double x, double y, double z) {
        vector = vector.subtract(x, y, z);
        return this;
    }

    public IVec3d add(double x, double y, double z, boolean permanent) {
        Vec3d adjusted = vector.add(x, y, z);

        if (permanent) {
            vector = adjusted;
        } else {
            fluentVector = adjusted;
        }

        return this;
    }

    public IVec3d add(double x, double y, double z) {
        return add(x, y, z, true);
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
