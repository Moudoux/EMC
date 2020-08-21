package me.deftware.client.framework.util.minecraft;

import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.math.position.DoubleBlockPosition;
import me.deftware.client.framework.math.vector.Vector3d;
import me.deftware.client.framework.util.hitresult.CrosshairResult;
import me.deftware.client.framework.world.EnumFacing;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

/**
 * @author Deftware
 */
public class BlockSwingResult extends CrosshairResult {

	public BlockSwingResult(Vector3d vector3d, EnumFacing facing, BlockPosition position, boolean inBlock) {
		super(new BlockHitResult(
				vector3d.getMinecraftVector(),
				facing.getFacing(),
				position.getMinecraftBlockPos(),
				inBlock
		));
	}

	public BlockSwingResult(HitResult result) {
		super(result);
	}

	public BlockPosition getBlockPosition() {
		return DoubleBlockPosition.fromMinecraftBlockPos(getMinecraftHitResult().getBlockPos());
	}

	public EnumFacing getFacing() {
		return EnumFacing.fromMinecraft(getMinecraftHitResult().getSide());
	}

	@Override
	public BlockHitResult getMinecraftHitResult() {
		return (BlockHitResult) hitResult;
	}

}
