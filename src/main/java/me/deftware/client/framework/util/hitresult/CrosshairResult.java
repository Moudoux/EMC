package me.deftware.client.framework.util.hitresult;

import me.deftware.client.framework.math.vector.Vector3d;
import net.minecraft.util.hit.HitResult;

/**
 * @author Deftware
 */
public class CrosshairResult {
	
	protected HitResult hitResult;
	protected final Vector3d vector3d;

	public CrosshairResult(HitResult hitResult) {
		this.hitResult = hitResult;
		this.vector3d = new Vector3d(hitResult.getPos());
	}

	public Vector3d getVector() {
		return vector3d;
	}

	public HitResult getMinecraftHitResult() {
		return hitResult;
	}

	public CrosshairResult setReference(HitResult result) {
		this.hitResult = result;
		this.vector3d.setVec3d(hitResult.getPos());
		return this;
	}

}
