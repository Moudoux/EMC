package me.deftware.client.framework.wrappers.world.blocks;

import me.deftware.client.framework.wrappers.world.IBlock;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import me.deftware.client.framework.wrappers.world.IWorld;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;

public class IBlockCrops extends IBlock {

	public IBlockCrops(String name) {
		super(name);
	}

	public IBlockCrops(Block block) {
		super(block);
	}

	public boolean isMaxAge(IBlockPos pos) {
		return ((BlockCrops) block).isMaxAge(IWorld.getStateFromPos(pos));
	}

}