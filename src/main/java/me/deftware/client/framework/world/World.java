package me.deftware.client.framework.world;

import com.google.gson.Gson;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.global.GameKeys;
import me.deftware.client.framework.global.GameMap;
import me.deftware.client.framework.global.types.BlockProperty;
import me.deftware.client.framework.global.types.PropertyManager;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.client.framework.util.WebUtils;
import me.deftware.client.framework.world.block.Block;
import me.deftware.client.framework.world.block.BlockState;
import me.deftware.client.framework.world.player.PlayerEntry;
import me.deftware.mixin.imp.IMixinNetworkHandler;
import me.deftware.mixin.imp.IMixinWorld;
import me.deftware.mixin.imp.IMixinWorldClient;
import net.fabricmc.loader.util.sat4j.minisat.constraints.cnf.Lits;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.LightType;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * @author Deftware, CDAGaming
 */
public class World {

	private static final float[] tickRates = new float[20];
	private static long timeLastTimeUpdate;
	private static int nextIndex = 0;

	public static boolean isLoaded() {
		return MinecraftClient.getInstance().world != null;
	}

	public static Stream<TileEntity> getLoadedTileEntities() {
		return Objects.requireNonNull(((IMixinWorld) MinecraftClient.getInstance().world)).getLoadedTilesAccessor().stream();
	}

	public static Stream<Entity> getLoadedEntities() {
		return Objects.requireNonNull(((IMixinWorldClient) MinecraftClient.getInstance().world)).getLoadedEntitiesAccessor().values().stream();
	}

	public static Entity getEntityById(int id) {
		return Objects.requireNonNull(((IMixinWorldClient) MinecraftClient.getInstance().world)).getLoadedEntitiesAccessor().getOrDefault(id, null);
	}

	public static int getDifficulty() {
		return Objects.requireNonNull(MinecraftClient.getInstance().world).getDifficulty().getId();
	}

	public static long getWorldTime() {
		return Objects.requireNonNull(MinecraftClient.getInstance().world).getTimeOfDay();
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

	public static int getBlockLightLevel(BlockPosition position) {
		return Objects.requireNonNull(MinecraftClient.getInstance().world).getLightLevel(LightType.BLOCK, position.getMinecraftBlockPos());
	}

	public static BlockState getStateFromBlockPos(BlockPosition position) {
		return new BlockState(Objects.requireNonNull(MinecraftClient.getInstance().world).getBlockState(position.getMinecraftBlockPos()));
	}

	public static Block getBlockFromPosition(BlockPosition position) {
		BlockState blockState = getStateFromBlockPos(position);
		Block block = Block.newInstance(blockState.getMinecraftBlockState().getBlock());
		block.setBlockPosition(position);
		block.setLocationBlockState(blockState);
		return block;
	}

	public static void addEntityToWorld(int id, Entity entity) {
		Objects.requireNonNull(MinecraftClient.getInstance().world).addEntity(id, entity.getMinecraftEntity());
	}

	public static void removeEntityFromWorld(int id) {
		Objects.requireNonNull(MinecraftClient.getInstance().world).removeEntity(id, net.minecraft.entity.Entity.RemovalReason.DISCARDED); // TODO: Verify this
	}

	public static void determineRenderState(net.minecraft.block.BlockState state, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
		if (state.getBlock() instanceof FluidBlock) {
			ci.setReturnValue(
					GameMap.INSTANCE.get(GameKeys.RENDER_FLUIDS, true)
			);
		} else {
			PropertyManager<BlockProperty> blockProperties = Bootstrap.blockProperties;
			if (blockProperties.isActive()) {
				int id = Registry.BLOCK.getRawId(state.getBlock());
				if (blockProperties.contains(id) && blockProperties.get(id).isRender())
					ci.setReturnValue(false);
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

	public static String getBiomeCategoryName() {
		net.minecraft.entity.Entity player = Objects.requireNonNull(MinecraftClient.getInstance().player);
		return player.world.getBiome(player.getBlockPos()).getCategory().getName();
	}

	public static double getTPS() {
		if (isLoaded()) {
			float numTicks = 0.0F;
			float sumTickRates = 0.0F;
			for (float tickRate : tickRates) {
				if (tickRate > 0.0F) {
					sumTickRates += tickRate;
					numTicks += 1.0F;
				}
			}
			return MathHelper.clamp(sumTickRates / numTicks, 0.0F, 20.0F);
		} else {
			return 0.0d;
		}
	}

	public static void updateTime() {
		if (timeLastTimeUpdate != -1L) {
			float timeElapsed = (float) (System.currentTimeMillis() - timeLastTimeUpdate) / 1000.0F;
			tickRates[(nextIndex % tickRates.length)] = MathHelper.clamp(20.0F / timeElapsed, 0.0F, 20.0F);
			nextIndex += 1;
		}
		timeLastTimeUpdate = System.currentTimeMillis();
	}

	public static CompletableFuture<String> getUsernameFromUUID(UUID uuid) {
		return CompletableFuture.supplyAsync(() -> {
			// Check local cache first
			ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
			if (networkHandler != null) {
				PlayerListEntry result = networkHandler.getPlayerListEntry(uuid);
				if (result != null) {
					return result.getProfile().getName();
				}
			}
			// Rate limit: You can request the same profile once per minute, however you can send as many unique requests as you like.
			try {
				String response = WebUtils.get("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString());
				PlayerData playerData = new Gson().fromJson(response, PlayerData.class);
				return playerData != null && playerData.name != null ? playerData.name : null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});
	}

	public static Map<UUID, PlayerEntry> getPlayerList() {
		if (MinecraftClient.getInstance().getNetworkHandler() != null) {
			return ((IMixinNetworkHandler) MinecraftClient.getInstance().getNetworkHandler()).getPlayerEntryMap();
		}
		return null;
	}

	private static class PlayerData {
		String id;
		String name;
	}

}
