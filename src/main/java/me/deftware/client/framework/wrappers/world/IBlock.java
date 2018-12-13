package me.deftware.client.framework.wrappers.world;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class IBlock {

	private Block block;

	public IBlock(int id) {
		block = IRegistry.BLOCK.get(id);
	}

	public IBlock(String name) {
		block = getBlockFromName(name);
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
		return IRegistry.BLOCK.getId(block);
	}

	public String getLocalizedName() {
		return block.getNameTextComponent().getUnformattedComponentText();
	}

	public String getTranslationKey() {
		return block.getTranslationKey();
	}

	public static boolean isValidBlock(int id) {
		return IRegistry.BLOCK.get(id) != null;
	}

	public static boolean isValidBlock(String id) {
		return getBlockFromName(id) != null;
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

	@Nullable
	public static Block getBlockFromName(String p_getBlockFromName_0_) {
		try {
			ResourceLocation lvt_1_1_ = new ResourceLocation(p_getBlockFromName_0_);
			if (IRegistry.BLOCK.containsKey(lvt_1_1_)) {
				return IRegistry.BLOCK.getOrDefault(lvt_1_1_);
			}
		} catch (IllegalArgumentException var2) {
			;
		}
		return null;
	}

}
