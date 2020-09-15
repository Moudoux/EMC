package me.deftware.mixin.mixins.world;

import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.event.events.EventTileBlockRemoved;
import me.deftware.client.framework.world.classifier.BlockClassifier;
import me.deftware.mixin.imp.IMixinWorld;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Mixin(World.class)
public abstract class MixinWorld implements IMixinWorld {

	@Shadow
	public abstract BlockEntity getBlockEntity(BlockPos pos);

	@Shadow @Final protected List<BlockEntity> unloadedBlockEntities;
	@Unique
	public final HashMap<BlockEntity, TileEntity> emcTileEntities = new HashMap<>();

	@Override
	@Unique
	public Collection<TileEntity> getLoadedTilesAccessor() {
		return emcTileEntities.values();
	}

	@Inject(method = "addBlockEntity", at = @At("TAIL"))
	public void addBlockEntity(BlockEntity blockEntity, CallbackInfoReturnable<Boolean> ci) {
		emcTileEntities.put(blockEntity, TileEntity.newInstance(blockEntity));
	}

	@Inject(method = "tickBlockEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;removeAll(Ljava/util/Collection;)Z", ordinal = 1))
	private void onRemoveEntityIf(CallbackInfo info) {
		for (BlockEntity entity : this.unloadedBlockEntities) {
			new EventTileBlockRemoved(emcTileEntities.remove(entity)).broadcast();
		}
	}

	@SuppressWarnings("RedundantCast")
	@Redirect(method = "tickBlockEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;remove(Ljava/lang/Object;)Z"))
	private boolean onRemoveEntity(List<BlockEntity> list, Object entity) {
		new EventTileBlockRemoved(emcTileEntities.remove((BlockEntity) entity)).broadcast();
		return list.remove((BlockEntity) entity);
	}

	@Inject(method = "removeBlockEntity", at = @At("HEAD"))
	public void removeBlockEntity(BlockPos pos, CallbackInfo info) {
		BlockEntity blockEntity = this.getBlockEntity(pos);
		if (blockEntity != null) {
			new EventTileBlockRemoved(emcTileEntities.remove(blockEntity)).broadcast();
		}
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
