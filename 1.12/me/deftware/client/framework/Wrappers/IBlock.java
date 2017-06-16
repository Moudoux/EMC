package me.deftware.client.framework.Wrappers;

import net.minecraft.block.Block;

public class IBlock {

	private Block block;

	public IBlock(int id) {
		this.block = Block.getBlockById(id);
	}

	public IBlock(String name) {
		this.block = Block.getBlockFromName(name);
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
