package me.deftware.client.framework.world.block.types;

import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.math.box.DoubleBoundingBox;
import me.deftware.client.framework.world.block.BlockState;
import net.minecraft.block.Block;
import net.minecraft.block.DoubleBlockProperties;

/**
 * @author Deftware
 */
public class ChestBlock extends StorageBlock {

	public ChestBlock(Block block) {
		super(block);
	}

	public BoundingBox getBoundingBox(BlockState state) {
		if (isDouble(state)) {
			switch (net.minecraft.block.ChestBlock.getFacing(state.getMinecraftBlockState())) {
				case NORTH:
					return new DoubleBoundingBox(getBlockPosition().getX(), getBlockPosition().getY(), getBlockPosition().getZ() - 1,
							getBlockPosition().getX() + 1.0, getBlockPosition().getY() + 1.0, getBlockPosition().getZ() + 1.0);
				case SOUTH:
					return new DoubleBoundingBox(getBlockPosition().getX(), getBlockPosition().getY(), getBlockPosition().getZ(),
							getBlockPosition().getX() + 1.0, getBlockPosition().getY() + 1.0, getBlockPosition().getZ() + 2.0);
				case WEST:
					return new DoubleBoundingBox(getBlockPosition().getX() - 1, getBlockPosition().getY(), getBlockPosition().getZ(),
							getBlockPosition().getX() + 1.0, getBlockPosition().getY() + 1.0, getBlockPosition().getZ() + 1.0);
				case EAST:
					return new DoubleBoundingBox(getBlockPosition().getX(), getBlockPosition().getY(), getBlockPosition().getZ(),
							getBlockPosition().getX() + 2.0, getBlockPosition().getY() + 1.0, getBlockPosition().getZ() + 1.0);
			}
		}
		return getBlockPosition().getBoundingBox();
	}

	public boolean isDouble(BlockState state) {
		return net.minecraft.block.ChestBlock.getDoubleBlockType(state.getMinecraftBlockState()) != DoubleBlockProperties.Type.SINGLE;
	}

}
