package me.deftware.client.framework.world.block;

import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.registry.BlockRegistry;
import me.deftware.client.framework.world.ClientWorld;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

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

	public boolean hasFluid() {
		return !blockState.getFluidState().isEmpty();
	}

	public boolean isFluidWater() {
		Fluid state = blockState.getFluidState().getFluid();
		return state == Fluids.WATER || state == Fluids.FLOWING_WATER;
	}

	public boolean isFluidFlowing() {
		Fluid state = blockState.getFluidState().getFluid();
		return state == Fluids.FLOWING_LAVA || state == Fluids.FLOWING_WATER;
	}

	public boolean isPathfindable(ClientWorld world) {
		return blockState.canPathfindThrough((BlockView) world, BlockPos.ORIGIN, NavigationType.LAND);
	}

	public boolean isBottomSlab() {
		if (blockState.getBlock() instanceof SlabBlock) {
			return blockState.get(SlabBlock.TYPE) == SlabType.BOTTOM;
		}
		return false;
	}

	public boolean isNormalCube() {
		net.minecraft.block.Block block = blockState.getBlock();
		if (block instanceof BambooBlock ||
			block instanceof PistonExtensionBlock ||
			block instanceof ScaffoldingBlock ||
			block instanceof ShulkerBoxBlock ||
			block instanceof PointedDripstoneBlock ||
			block instanceof AmethystClusterBlock
		) {
			return false;
		}
		return net.minecraft.block.Block.isShapeFullCube(blockState.getCollisionShape(null, null));
	}

	public int getSnowLayers() {
		if (blockState.getBlock() instanceof SnowBlock) {
			return blockState.get(SnowBlock.LAYERS);
		}
		return 0;
	}

}
