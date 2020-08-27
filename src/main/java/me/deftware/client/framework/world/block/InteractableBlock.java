package me.deftware.client.framework.world.block;

import net.minecraft.block.*;

/**
 * @author Deftware
 */
public class InteractableBlock extends Block {

	public InteractableBlock(net.minecraft.block.Block block) {
		super(block);
	}

	public static boolean isInteractable(net.minecraft.block.Block block) {
		return block instanceof AbstractButtonBlock || block instanceof BlockWithEntity || block instanceof AnvilBlock ||
				block instanceof BedBlock || block instanceof CakeBlock || block instanceof CartographyTableBlock ||
				block instanceof CauldronBlock || block instanceof AbstractRedstoneGateBlock || block instanceof ComposterBlock ||
				block instanceof CraftingTableBlock || block instanceof DoorBlock || block instanceof DragonEggBlock ||
				block instanceof FenceBlock || block instanceof FenceGateBlock || block instanceof FlowerPotBlock ||
				block instanceof GrindstoneBlock || block instanceof JigsawBlock || block instanceof LeverBlock ||
				block instanceof LoomBlock || block instanceof NoteBlock || block instanceof RespawnAnchorBlock ||
				block instanceof StonecutterBlock || block instanceof TrapdoorBlock;
	}

}
