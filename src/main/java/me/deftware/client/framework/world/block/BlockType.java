package me.deftware.client.framework.world.block;

import net.minecraft.block.*;

/**
 * @author Deftware
 */
public enum BlockType {

	// Types
	BlockContainer, BlockCrops, FluidBlock,

	// Specific blocks
	BlockPumpkin, BlockMelon, BlockReed, BlockCactus, BlockNetherWart, BlockFarmland, BlockSoulSand;

	public boolean instanceOf(Block emcBlock) {
		net.minecraft.block.Block block = emcBlock.getMinecraftBlock();
		if (this.equals(BlockContainer)) {
			return block instanceof BlockWithEntity;
		} else if (this.equals(BlockCrops)) {
			return block instanceof CropBlock;
		} else if (this.equals(BlockPumpkin)) {
			return block instanceof PumpkinBlock;
		} else if (this.equals(BlockMelon)) {
			return block instanceof MelonBlock;
		} else if (this.equals(BlockReed)) {
			return block instanceof SugarCaneBlock;
		} else if (this.equals(BlockCactus)) {
			return block instanceof CactusBlock;
		} else if (this.equals(BlockNetherWart)) {
			return block instanceof NetherWartBlock;
		} else if (this.equals(BlockFarmland)) {
			return block instanceof FarmlandBlock;
		} else if (this.equals(BlockSoulSand)) {
			return block instanceof SoulSandBlock;
		} else if (this.equals(FluidBlock)) {
			return block instanceof FluidBlock;
		}
		return false;
	}
	
}
