package me.deftware.client.framework.world;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.world.block.Block;
import me.deftware.client.framework.world.block.BlockState;
import me.deftware.mixin.imp.IMixinWorld;
import me.deftware.mixin.imp.IMixinWorldClient;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Deftware, CDAGaming
 */
public class World {

	private static long previousTotalWorldTime;
	private static double previousMeasureTime, currentTPS = 0;

	public static boolean isLoaded() {
		return MinecraftClient.getInstance().world != null;
	}

	public static Stream<TileEntity> getLoadedTileEntities() {
		return Objects.requireNonNull(((IMixinWorld) MinecraftClient.getInstance().world)).getLoadedTilesAccessor().stream();
	}

	public static Stream<Entity> getLoadedEntities() {
		return Objects.requireNonNull(((IMixinWorldClient) MinecraftClient.getInstance().world)).getLoadedEntitiesAccessor().values().stream();
	}

	public static long getWorldTime() {
		return Objects.requireNonNull(MinecraftClient.getInstance().world).getTime();
	}

	public static void sendQuittingPacket() {
		Objects.requireNonNull(MinecraftClient.getInstance().world).disconnect();
	}

	public static void leaveWorld() {
		MinecraftClient.getInstance().joinWorld(null);
	}

	public static int getWorldHeight() {
		return Objects.requireNonNull(MinecraftClient.getInstance().world).getHeight();
	}

	public static BlockState getStateFromBlockPos(BlockPosition position) {
		return new BlockState(Objects.requireNonNull(MinecraftClient.getInstance().world).getBlockState(position.getMinecraftBlockPos()));
	}

	public static Block getBlockFromPosition(BlockPosition position) {
		BlockState blockState = getStateFromBlockPos(position);
		Block block = Block.newInstance(blockState.getMinecraftBlockState().getBlock());
		block.setBlockPosition(position);
		return block;
	}

	public static void addEntityToWorld(int id, Entity entity) {
		Objects.requireNonNull(MinecraftClient.getInstance().world).addEntity(id, entity.getMinecraftEntity());
	}

	public static void removeEntityFromWorld(int id) {
		Objects.requireNonNull(MinecraftClient.getInstance().world).removeEntity(id);
	}

	public static void determineRenderState(net.minecraft.block.BlockState state, CallbackInfoReturnable<Boolean> ci) {
		if (state.getBlock() instanceof FluidBlock) {
			ci.setReturnValue(((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "FLUIDS", true)));
		} else {
			if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(state.getBlock()), "render"))) {
				boolean doRender = (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(state.getBlock()), "render", false);
				if (!doRender) {
					ci.setReturnValue(false);
				}
			}
		}
	}

	/**
	 * Which dimension the player is in (-1 = the Nether, 0 = normal world)
	 *
	 * @return The dimension
	 */
	public static int getDimension() {
		net.minecraft.world.World dimension = Objects.requireNonNull(MinecraftClient.getInstance().player).world;
		return dimension.getRegistryKey() == net.minecraft.world.World.OVERWORLD ? 0 :
				dimension.getRegistryKey() == net.minecraft.world.World.END ? 1 :
						dimension.getRegistryKey() == net.minecraft.world.World.NETHER ? -1
								: dimension.getDimension().hashCode();
	}

	public static double getTPS() {
		if (World.isLoaded()) {
			if (World.getWorldTime() == 0) return 0.0D;
			if (getTimeInSeconds() - previousMeasureTime < 3.0) {
				return currentTPS;
			}
			currentTPS = ((double) (World.getWorldTime() - previousTotalWorldTime)) / (getTimeInSeconds() - previousMeasureTime);

			// Limits TPS to not go above 20, sometimes possible
			if (currentTPS > 20.0d) {
				currentTPS = 20.0d;
			}
			// Also prevent it going below 20, which is also sometimes possible
			if (currentTPS < 0.0d) {
				currentTPS = 0.0d;
			}

			updatePreviousTotalWorldTime();
		} else {
			currentTPS = 0.0d;
		}
		return currentTPS;
	}

	private static void updatePreviousTotalWorldTime() {
		previousTotalWorldTime = World.getWorldTime();
		previousMeasureTime = getTimeInSeconds();
	}

	public static double getTimeInSeconds() {
		return (System.currentTimeMillis() / 1000d);
	}

}
