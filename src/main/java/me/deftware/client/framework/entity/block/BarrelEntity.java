package me.deftware.client.framework.entity.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.chunk.BlockEntityTickInvoker;

/**
 * @author Deftware
 */
public class BarrelEntity extends StorageEntity {

	public BarrelEntity(BlockEntity entity, BlockEntityTickInvoker ticker) {
		super(entity, ticker);
	}

}
