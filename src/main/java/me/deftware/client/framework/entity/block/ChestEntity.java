package me.deftware.client.framework.entity.block;

import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.math.box.DoubleBoundingBox;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;

/**
 * @author Deftware
 */
public class ChestEntity extends StorageEntity {

	private final BoundingBox DOUBLE_NORTH, DOUBLE_SOUTH, DOUBLE_WEST, DOUBLE_EAST;

	public ChestEntity(BlockEntity entity) {
		super(entity);
		DOUBLE_NORTH = new DoubleBoundingBox(getBlockPosition().getX(), getBlockPosition().getY(), getBlockPosition().getZ() - 1,
				getBlockPosition().getX() + 1.0, getBlockPosition().getY() + 1.0, getBlockPosition().getZ() + 1.0);
		DOUBLE_SOUTH = new DoubleBoundingBox(getBlockPosition().getX(), getBlockPosition().getY(), getBlockPosition().getZ(),
				getBlockPosition().getX() + 1.0, getBlockPosition().getY() + 1.0, getBlockPosition().getZ() + 2.0);
		DOUBLE_WEST = new DoubleBoundingBox(getBlockPosition().getX() - 1, getBlockPosition().getY(), getBlockPosition().getZ(),
				getBlockPosition().getX() + 1.0, getBlockPosition().getY() + 1.0, getBlockPosition().getZ() + 1.0);
		DOUBLE_EAST = new DoubleBoundingBox(getBlockPosition().getX(), getBlockPosition().getY(), getBlockPosition().getZ(),
				getBlockPosition().getX() + 2.0, getBlockPosition().getY() + 1.0, getBlockPosition().getZ() + 1.0);
	}

	@Override
	public BoundingBox getBoundingBox() {
		if (isDouble()) {
			switch (ChestBlock.getFacing(entity.getCachedState())) {
				case NORTH:
					return DOUBLE_NORTH;
				case SOUTH:
					return DOUBLE_SOUTH;
				case WEST:
					return DOUBLE_WEST;
				case EAST:
					return DOUBLE_EAST;
			}
		}
		return SINGLE;
	}

	public boolean isFirst() {
		return !isEnderChest() && ChestBlock.getDoubleBlockType(entity.getCachedState()) == DoubleBlockProperties.Type.FIRST;
	}

	public boolean isDouble() {
		return !isEnderChest() && ChestBlock.getDoubleBlockType(entity.getCachedState()) != DoubleBlockProperties.Type.SINGLE;
	}

	public boolean isEnderChest() {
		return entity instanceof EnderChestBlockEntity;
	}

	public boolean isTrapped() {
		return entity instanceof TrappedChestBlockEntity;
	}

}
