package me.deftware.client.framework.world;

import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.registry.BlockRegistry;
import me.deftware.client.framework.world.block.Block;
import me.deftware.client.framework.world.block.BlockState;
import me.deftware.client.framework.world.chunk.ChunkAccessor;
import net.minecraft.block.entity.BlockEntity;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Represents an in-game world
 *
 * @since 17.0.0
 * @author Deftware
 */
public interface World {

	/**
	 * @return A stream of a all block entities
	 */
	Stream<TileEntity> getLoadedTileEntities();

	/**
	 * @return The world difficulty
	 */
	int _getDifficulty();

	/**
	 * @return The world time
	 */
	long _getWorldTime();

	/**
	 * @return The world height
	 */
	int _getWorldHeight();

	/**
	 * @return The current world biome
	 */
	Biome _getBiome();

	/**
	 * @return The light level for a given block position
	 */
	int _getBlockLightLevel(BlockPosition position);

	/**
	 * Disconnects from the world/server
	 */
	void _disconnect();

	/**
	 * @return A chunk in the world
	 */
	ChunkAccessor getChunk(int x, int z);

	/**
	 * End = 1
	 * Overworld = 0
	 * Nether = -1
	 *
	 * @return The dimension this world is
	 */
	int _getDimension();

	/**
	 * @param position A block position
	 * @return The state for a given position
	 */
	BlockState _getBlockState(BlockPosition position);

	/**
	 * @param position A block position
	 * @return The block in a given position
	 */
	default Block _getBlockFromPosition(BlockPosition position) {
		BlockState blockState = _getBlockState(position);
		Block block = BlockRegistry.INSTANCE.getBlock(blockState.getMinecraftBlockState().getBlock());
		block.setBlockPosition(position);
		block.setLocationBlockState(blockState);
		return block;
	}

	/**
	 * Converts a Minecraft block entity to an EMC entity
	 *
	 * @param reference A Minecraft block entity
	 * @return An EMC {@link TileEntity}
	 */
	@ApiStatus.Internal
	<T extends TileEntity> T getTileEntityByReference(BlockEntity reference);

	@ApiStatus.Internal
	HashMap<Long, BlockEntity> getInternalLongToBlockEntity();

}
