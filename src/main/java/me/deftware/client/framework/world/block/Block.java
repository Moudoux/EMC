package me.deftware.client.framework.world.block;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.item.IItem;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.world.block.types.CropBlock;
import me.deftware.client.framework.world.block.types.ShulkerBlock;
import me.deftware.client.framework.world.block.types.StorageBlock;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

/**
 * @author Deftware
 */
public class Block implements IItem {

	protected final net.minecraft.block.Block block;
	protected BlockPosition blockPosition;
	private @Setter @Getter BlockState locationBlockState = null;

	public static Block newInstance(net.minecraft.block.Block block) {
		if (block instanceof net.minecraft.block.CropBlock) {
			return new CropBlock(block);
		} else if (block instanceof ShulkerBoxBlock) {
			return new ShulkerBlock(block);
		} else if (block instanceof ChestBlock || block instanceof BarrelBlock || block instanceof EnderChestBlock) {
			return StorageBlock.newInstance(block);
		}
		return new Block(block);
	}

	protected Block(net.minecraft.block.Block block) {
		this.block = block;
	}

	public void setBlockPosition(BlockPosition position) {
		this.blockPosition = position;
	}

	public BlockPosition getBlockPosition() {
		return blockPosition;
	}

	public net.minecraft.block.Block getMinecraftBlock() {
		return block;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Block) {
			return ((Block) object).getMinecraftBlock() == getMinecraftBlock() ||
					((Block) object).getMinecraftBlock().getTranslationKey().equalsIgnoreCase(getMinecraftBlock().getTranslationKey());
		}
		return false;
	}

	public BlockState getDefaultBlockState() {
		return new BlockState(block.getDefaultState());
	}

	public boolean isAir() {
		return block == Blocks.AIR;
	}

	public boolean isCaveAir() {
		return block == Blocks.CAVE_AIR;
	}

	public boolean isLiquid() {
		return block == Blocks.WATER || block == Blocks.LAVA || block instanceof FluidBlock;
	}

	public int getID() {
		return Registry.BLOCK.getRawId(block);
	}

	public ChatMessage getName() {
		return new ChatMessage().fromText(block.getName());
	}

	public String getIdentifierKey() {
		return getTranslationKey().substring("block.minecraft.".length());
	}

	public String getTranslationKey() {
		return block.getTranslationKey();
	}

	public boolean instanceOf(BlockType type) {
		return type.instanceOf(this);
	}

	@Override
	public Item getAsItem() {
		return getMinecraftBlock().asItem();
	}

}
