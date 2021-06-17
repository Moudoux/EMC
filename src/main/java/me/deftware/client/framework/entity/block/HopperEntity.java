package me.deftware.client.framework.entity.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.chunk.BlockEntityTickInvoker;

/**
 * @author Deftware
 */
public class HopperEntity extends StorageEntity {

	public HopperEntity(BlockEntity entity, BlockEntityTickInvoker ticker) {
		super(entity, ticker);
	}

}
