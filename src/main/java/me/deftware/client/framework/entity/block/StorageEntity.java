package me.deftware.client.framework.entity.block;

import net.minecraft.block.entity.*;

/**
 * @author Deftware
 */
public class StorageEntity extends TileEntity {

	protected final StorageType type;
	
	public StorageEntity(BlockEntity entity) {
		super(entity);
		type = entity instanceof ChestBlockEntity
				? entity instanceof TrappedChestBlockEntity ? StorageType.TRAPPED_CHEST : StorageType.CHEST
				: entity instanceof EnderChestBlockEntity ? StorageType.ENDER_CHEST
				: entity instanceof ShulkerBoxBlockEntity ? StorageType.SHULKER_BOX
				: entity instanceof BarrelBlockEntity ? StorageType.BARREL : StorageType.UNKNOWN;
	}

	public StorageType getType() {
		return type;
	}
	
	public enum StorageType {
		TRAPPED_CHEST, CHEST, ENDER_CHEST, SHULKER_BOX, BARREL, UNKNOWN
	}

}
