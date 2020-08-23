package me.deftware.mixin.mixins;

import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.mixin.imp.IMixinWorld;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

	@Redirect(method = "tickBlockEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;removeAll(Ljava/util/Collection;)Z", ordinal = 1))
	private boolean onRemoveEntityIf(List<BlockEntity> list, Collection<BlockEntity> entities) {
		for (BlockEntity entity : entities) emcTileEntities.remove(entity);
		return list.removeAll(entities);
	}

	@SuppressWarnings("RedundantCast")
	@Redirect(method = "tickBlockEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;remove(Ljava/lang/Object;)Z"))
	private boolean onRemoveEntity(List<BlockEntity> list, Object entity) {
		emcTileEntities.remove((BlockEntity) entity);
		return list.remove((BlockEntity) entity);
	}

	@Inject(method = "removeBlockEntity", at = @At("HEAD"))
	public void removeBlockEntity(BlockPos pos, CallbackInfo info) {
		BlockEntity blockEntity = this.getBlockEntity(pos);
		if (blockEntity != null) {
			emcTileEntities.remove(blockEntity);
		}
	}

}
