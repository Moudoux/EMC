package me.deftware.mixin.mixins.world;

import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.world.classifier.BlockClassifier;
import me.deftware.mixin.imp.IMixinWorld;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockEntityTickInvoker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Mixin(World.class)
public abstract class MixinWorld implements IMixinWorld {

	@Unique
	public final HashMap<BlockEntity, TileEntity> emcTileEntities = new HashMap<>();

	@Unique
	public final HashMap<Long, BlockEntity> longTileEntities = new HashMap<>();

	@Unique
	@Override
	public Map<BlockEntity, TileEntity> getLoadedTilesAccessor() {
		return emcTileEntities;
	}

	@Unique
	@Override
	public HashMap<Long, BlockEntity> getInternalLongToBlockEntity() {
		return longTileEntities;
	}

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

	@Inject(method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z", at = @At("TAIL"))
	public void setBlockState(BlockPos pos, BlockState state, int flags, int maxUpdateDepth, CallbackInfoReturnable<Boolean> info) {
		if (state.isAir()) {
			BlockClassifier.getClassifiers().forEach(blockClassifier -> {
				if (blockClassifier.getClassifiedBlocks().containsKey(pos.asLong())) {
					blockClassifier.getClassifiedBlocks().remove(pos.asLong());
				}
			});
		}
	}

}
