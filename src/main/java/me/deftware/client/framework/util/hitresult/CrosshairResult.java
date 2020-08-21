package me.deftware.client.framework.util.hitresult;

import me.deftware.client.framework.math.vector.Vector3d;
import net.minecraft.util.hit.HitResult;

/**
 * @author Deftware
 */
public class CrosshairResult {
	
	protected final HitResult hitResult;

	public CrosshairResult(HitResult hitResult) {
		this.hitResult = hitResult;
	}

	public Vector3d getVector() {
		return new Vector3d(hitResult.getPos());
	}

	public HitResult getMinecraftHitResult() {
		return hitResult;
	}

}
