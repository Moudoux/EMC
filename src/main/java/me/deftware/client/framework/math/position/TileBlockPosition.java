package me.deftware.client.framework.math.position;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

/**
 * @author Deftware
 */
public class TileBlockPosition extends BlockPosition {

	protected final BlockEntity entity;

	public TileBlockPosition(BlockEntity entity) {
		super(null);
		this.entity = entity;
	}

	@Override
	public double getX() {
		return entity.getPos().getX();
	}

	@Override
	public double getY() {
		return entity.getPos().getY();
	}

	@Override
	public double getZ() {
		return entity.getPos().getZ();
	}

	@Override
	public BlockPos getMinecraftBlockPos() {
		return entity.getPos();
	}

}
