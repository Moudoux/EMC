package me.deftware.client.framework.entity.block;

import net.minecraft.block.entity.*;
import net.minecraft.world.chunk.BlockEntityTickInvoker;

/**
 * @author Deftware
 */
public class StorageEntity extends TileEntity {

	public static StorageEntity newInstance(BlockEntity entity, BlockEntityTickInvoker ticker) {
		if (entity instanceof ChestBlockEntity || entity instanceof EnderChestBlockEntity) {
			return new ChestEntity(entity, ticker);
		} else if (entity instanceof BarrelBlockEntity) {
			return new BarrelEntity(entity, ticker);
		} else if (entity instanceof ShulkerBoxBlockEntity) {
			return new ShulkerEntity(entity, ticker);
		} else if (entity instanceof HopperBlockEntity) {
			return new HopperEntity(entity, ticker);
		}
		return new StorageEntity(entity, ticker);
	}

	protected StorageEntity(BlockEntity entity, BlockEntityTickInvoker ticker) {
		super(entity, ticker);
	}

}
