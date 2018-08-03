package me.deftware.client.framework.wrappers.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.resources.I18n;

public class IBlock {

	private Block block;

	public IBlock(int id) {
		block = Block.getBlockById(id);
	}

	public IBlock(String name) {
		block = Block.getBlockFromName(name);
	}

	public IBlock(Block block) {
		this.block = block;
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
		return Block.REGISTRY.getIDForObject(block);
	}

	public String getLocalizedName() {
		return block.getNameTextComponent().getUnformattedComponentText();
	}

	public static boolean isValidBlock(int id) {
		return Block.getBlockById(id) != null;
	}

	public static boolean isValidBlock(String id) {
		return Block.getBlockFromName(id) != null;
	}

	public boolean instanceOf(IBlockTypes type) {
		if (type.equals(IBlockTypes.BlockContainer)) {
			return block instanceof BlockContainer;
		}
		return false;
	}

	public enum IBlockTypes {
		BlockContainer
	}

}
