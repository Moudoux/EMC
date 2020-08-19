package me.deftware.client.framework.world.block;

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

	public Material getMaterial() {
		return material;
	}

}
