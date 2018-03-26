package me.deftware.client.framework.wrappers.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 *
 */
public class IBlock {

	private Block block;

	public IBlock(Block block) {
		this.block = block;
	}

	public IBlock(int id) {
		block = Block.getBlockById(id);
	}

	public IBlock(String name) {
		block = Block.getBlockFromName(name);
	}

	public boolean isValidBlock() {
		return block != null;
	}

	public boolean isAir() {
		return block == Blocks.AIR;
	}

	public Block getBlock() {
		return block;
	}

	public int getID() {
		return block.getIdFromBlock(block);
	}

	public String getLocalizedName() {
		return block.getLocalizedName();
	}

	public static boolean isValidBlock(int id) {
		return Block.getBlockById(id) != null;
	}

	public static boolean isValidBlock(String id) {
		return Block.getBlockFromName(id) != null;
	}

}
