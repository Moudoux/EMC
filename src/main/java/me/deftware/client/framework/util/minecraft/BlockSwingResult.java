package me.deftware.client.framework.util.minecraft;

import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.math.vector.Vector3d;
import me.deftware.client.framework.world.EnumFacing;
import net.minecraft.util.hit.BlockHitResult;

/**
 * @author Deftware
 */
public class BlockSwingResult {

	private final BlockHitResult hitResult;

	public BlockSwingResult(Vector3d vector3d, EnumFacing facing, BlockPosition position, boolean inBlock) {
		this.hitResult = new BlockHitResult(
				vector3d.getMinecraftVector(),
				facing.getFacing(),
				position.getMinecraftBlockPos(),
				inBlock
		);
	}

	public BlockHitResult getMinecraftHitResult() {
		return hitResult;
	}

}
