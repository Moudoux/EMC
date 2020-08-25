package me.deftware.client.framework.world.block.types;

import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.world.block.Block;
import me.deftware.client.framework.world.block.BlockState;
import net.minecraft.block.ChestBlock;

/**
 * @author Deftware
 */
public class StorageBlock extends Block {

	public static StorageBlock newInstance(net.minecraft.block.Block block) {
		if (block instanceof ChestBlock) {
			return new me.deftware.client.framework.world.block.types.ChestBlock(block);
		}
		return new StorageBlock(block);
	}

	public BoundingBox getBoundingBox(BlockState state) {
		return getBlockPosition().getBoundingBox();
	}
	
	protected StorageBlock(net.minecraft.block.Block block) {
		super(block);
	}

}
