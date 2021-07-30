package me.deftware.client.framework.world.block;

import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.registry.BlockRegistry;

/**
 * @author Deftware
 */
public class BlockState {

	protected final net.minecraft.block.BlockState blockState;
	protected final Material material;

	public BlockState(net.minecraft.block.BlockState blockState) {
		this.blockState = blockState;
		this.material = new Material(blockState.getMaterial());
	}

	public net.minecraft.block.BlockState getMinecraftBlockState() {
		return blockState;
	}

	public Block getBlock(BlockPosition position) {
		Block block = BlockRegistry.INSTANCE.getBlock(getMinecraftBlockState().getBlock());
		block.setBlockPosition(position);
		return block;
	}

	public Material getMaterial() {
		return material;
	}

}
