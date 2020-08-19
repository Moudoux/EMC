package me.deftware.client.framework.math.box;

import me.deftware.client.framework.math.vector.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Deftware
 */
public class BoundingBox {

	protected final Entity entity;
	public double offsetX = 0, offsetY = 0, offsetZ = 0;

	public BoundingBox(Entity entity) {
		this.entity = entity;
	}

	public Box getMinecraftBox() {
		return entity.getBoundingBox();
	}

	public Box getOffsetMinecraftBox() {
		return getOffsetMinecraftBox(offsetX, offsetY, offsetZ);
	}

	public Box getOffsetMinecraftBox(double offsetX, double offsetY, double offsetZ) {
		return getMinecraftBox().offset(offsetX, offsetY, offsetZ);
	}

	public BoundingBox offset(double offsetX, double offsetY, double offsetZ) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		return this;
	}

	public Vector3d getCenter() {
		return new Vector3d(getMinecraftBox().getCenter());
	}

	public double getMinX() {
		return getMinecraftBox().minX + offsetX;
	}

	public double getMinY() {
		return getMinecraftBox().minY + offsetY;
	}

	public double getMinZ() {
		return getMinecraftBox().minZ + offsetZ;
	}

	public double getMaxX() {
		return getMinecraftBox().maxX + offsetX;
	}

	public double getMaxY() {
		return getMinecraftBox().maxY + offsetY;
	}

	public double getMaxZ() {
		return getMinecraftBox().maxZ + offsetZ;
	}

	@Nullable
	public Vector3d rayTrace(Vector3d min, Vector3d max) {
		Optional<Vec3d> vec3d = getMinecraftBox().rayTrace(min.getMinecraftVector(), max.getMinecraftVector());
		return vec3d.map(Vector3d::new).orElse(null);
	}

}
