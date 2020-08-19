package me.deftware.client.framework.math.vector;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.math.position.BlockPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RayTraceContext;

import java.util.Objects;

/**
 * @author Deftware
 */
public class Vector3d {

	public static final Vector3d ZERO = new Vector3d(0.0D, 0.0D, 0.0D);

	protected Vec3d vec3d;

	public Vector3d(double x, double y, double z) {
		vec3d = new Vec3d(x, y, z);
	}

	public Vector3d(Vec3i vec3i) {
		vec3d = new Vec3d(vec3i.getX(), vec3i.getY(), vec3i.getZ());
	}

	public Vector3d(Vec3d vec3d) {
		this.vec3d = vec3d;
	}

	public Vector3d(BlockPosition position) {
		this(position.getX(), position.getY(), position.getZ());
	}

	public Vector3d(Entity entity) {
		this(entity.getBlockPosition());
	}

	public Vector3d(TileEntity entity) {
		this(entity.getBlockPosition());
	}

	public Vec3d getMinecraftVector() {
		return vec3d;
	}

	public double getX() {
		return vec3d.getX();
	}

	public double getY() {
		return vec3d.getY();
	}

	public double getZ() {
		return vec3d.getZ();
	}

	public Vector3d multiply(double factor) {
		vec3d = vec3d.multiply(factor);
		return this;
	}

	public Vector3d subtract(double x, double y, double z) {
		vec3d = vec3d.subtract(x, y, z);
		return this;
	}

	public Vector3d add(double x, double y, double z) {
		vec3d = vec3d.add(x, y, z);
		return this;
	}

	public Vector3d set(double x, double y, double z) {
		if (x == 0) x = getX();
		if (y == 0) y = getY();
		if (z == 0) z = getZ();
		vec3d = new Vec3d(x, y, z);
		return this;
	}

	public Vector3d subtract(Vector3d vector3d) {
		vec3d = vec3d.subtract(vector3d.getMinecraftVector());
		return this;
	}

	public Vector3d add(Vector3d vector3d) {
		vec3d = vec3d.add(vector3d.getMinecraftVector());
		return this;
	}

	public double squareDistanceTo(Vector3d vec) {
		return vec3d.distanceTo(vec.getMinecraftVector());
	}

	public static boolean rayTraceBlocks(Vector3d vec1, Vector3d vec2) {
		return Objects.requireNonNull(MinecraftClient.getInstance().world).
				rayTrace(new RayTraceContext(vec1.getMinecraftVector(), vec2.getMinecraftVector(), RayTraceContext.ShapeType.OUTLINE, RayTraceContext.FluidHandling.NONE,
						Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity())))
				.getType() != HitResult.Type.MISS;
	}

}
