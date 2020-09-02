package me.deftware.client.framework.math.vector;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.math.position.BlockPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;

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

	public Vector3d(Vec3i vec) {
		vec3d = new Vec3d(vec.getX(), vec.getY(), vec.getZ());
	}

	public Vector3d(Vec3d vec) {
		this.vec3d = vec;
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

	public Vector3d subtract(Vector3d vec) {
		vec3d = vec3d.subtract(vec.getMinecraftVector());
		return this;
	}

	public Vector3d add(Vector3d vec) {
		vec3d = vec3d.add(vec.getMinecraftVector());
		return this;
	}

	public double squareDistanceTo(Vector3d vec) {
		return vec3d.distanceTo(vec.getMinecraftVector());
	}

	public static boolean rayTraceBlocks(Vector3d start, Vector3d end) {
		return Objects.requireNonNull(MinecraftClient.getInstance().world).
				raycast(new RaycastContext(start.getMinecraftVector(), end.getMinecraftVector(), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE,
						Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity())))
				.getType() != HitResult.Type.MISS;
	}

	public double getMagnitude() {
		return Math.sqrt(vec3d.x*vec3d.x+vec3d.y*vec3d.y+vec3d.z*vec3d.z);
	}

}
