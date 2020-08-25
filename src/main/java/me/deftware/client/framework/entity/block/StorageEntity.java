package me.deftware.client.framework.entity.block;

import net.minecraft.block.entity.*;

/**
 * @author Deftware
 */
public class StorageEntity extends TileEntity {

	public static StorageEntity newInstance(BlockEntity entity) {
		if (entity instanceof ChestBlockEntity || entity instanceof EnderChestBlockEntity) {
			return new ChestEntity(entity);
		} else if (entity instanceof BarrelBlockEntity) {
			return new BarrelEntity(entity);
		} else if (entity instanceof ShulkerBoxBlockEntity) {
			return new ShulkerEntity(entity);
		} else if (entity instanceof HopperBlockEntity) {
			return new HopperEntity(entity);
		}
		return new StorageEntity(entity);
	}

	protected StorageEntity(BlockEntity entity) {
		super(entity);
	}

}
