package me.deftware.mixin.mixins.world;

import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.world.Biome;
import me.deftware.client.framework.world.chunk.ChunkAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockEntityTickInvoker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * @author Deftware
 */
@Mixin(World.class)
public class MixinWorld implements me.deftware.client.framework.world.World {

	@Unique
	public final HashMap<BlockEntity, TileEntity> emcTileEntities = new HashMap<>();

	@Unique
	public final HashMap<Long, BlockEntity> longTileEntities = new HashMap<>();

	@Inject(method = "addBlockEntityTicker", at = @At("HEAD"))
	public void addBlockEntityTicker(BlockEntityTickInvoker blockEntityTickInvoker, CallbackInfo ci) {
		if (longTileEntities.containsKey(blockEntityTickInvoker.getPos().asLong())) {
			BlockEntity entity = longTileEntities.remove(blockEntityTickInvoker.getPos().asLong());
			emcTileEntities.put(entity, TileEntity.newInstance(entity, blockEntityTickInvoker));
		}
	}

	@Redirect(method = "tickBlockEntities", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;", opcode = 180))
	protected Object tickBlockEntities(Iterator<BlockEntityTickInvoker> iterator) {
		BlockEntityTickInvoker ticker = iterator.next();
		if (ticker.isRemoved()) {
			emcTileEntities.values().removeIf(e -> e.getTicker().equals(ticker));
		}
		return ticker;
	}

	@Override
	public Stream<TileEntity> getLoadedTileEntities() {
		return emcTileEntities.values().stream();
	}

	@Override
	public int _getDifficulty() {
		return ((World) (Object) this).getDifficulty().getId();
	}

	@Override
	public long _getWorldTime() {
		return ((World) (Object) this).getTimeOfDay();
	}

	@Override
	public int _getWorldHeight() {
		return ((World) (Object) this).getHeight();
	}

	@Override
	public int _getBlockLightLevel(BlockPosition position) {
		return ((World) (Object) this).getLightLevel(position.getMinecraftBlockPos());
	}

	@Override
	public void _disconnect() {
		MinecraftClient mc = MinecraftClient.getInstance();
		boolean sp = mc.isInSingleplayer();
		((World) (Object) this).disconnect();
		if (sp) {
			mc.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
		} else {
			mc.disconnect(null);
		}
	}

	@Override
	public ChunkAccessor getChunk(int x, int z) {
		return (ChunkAccessor) ((World) (Object) this).getChunk(x, z);
	}

	@Override
	public boolean _hasChunk(int x, int z) {
		return ((World) (Object) this).isChunkLoaded(x, z);
	}

	@Override
	public int _getDimension() {
		RegistryKey<World> key = ((World) (Object) this).getRegistryKey();
		if (World.END.equals(key)) {
			return 1;
		} else if (World.OVERWORLD.equals(key)) {
			return 0;
		} else if (World.NETHER.equals(key)) {
			return -1;
		}
		return -2;
	}

	@Override
	public me.deftware.client.framework.world.block.BlockState _getBlockState(BlockPosition position) {
		return new me.deftware.client.framework.world.block.BlockState(
				((World) (Object) this).getBlockState(position.getMinecraftBlockPos())
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends TileEntity> T getTileEntityByReference(BlockEntity reference) {
		if (reference != null) {
			return (T) emcTileEntities.get(reference);
		}
		return null;
	}

	@Override
	public HashMap<Long, BlockEntity> getInternalLongToBlockEntity() {
		return longTileEntities;
	}

	@Unique
	private static final Biome _biome = new Biome();

	@Override
	public Biome _getBiome() {
		return _biome.setReference(
				((ClientWorld) (Object) this).getBiome(
						MinecraftClient.getInstance().player.getBlockPos()
				)
		);
	}

}
