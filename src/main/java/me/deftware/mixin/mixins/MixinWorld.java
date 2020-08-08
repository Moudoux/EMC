package me.deftware.mixin.mixins;

import me.deftware.client.framework.wrappers.entity.ITileEntity;
import me.deftware.mixin.imp.IMixinWorld;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Mixin(World.class)
public abstract class MixinWorld implements IMixinWorld {

	@Shadow
	@Final
	public List<BlockEntity> blockEntities;

	@Shadow
	@Final
	protected List<BlockEntity> unloadedBlockEntities;

	@Shadow
	public abstract BlockEntity getBlockEntity(BlockPos pos);

	@Unique
	public final HashMap<BlockEntity, ITileEntity> emcTileEntities = new HashMap<>();

	@Override
	public Collection<ITileEntity> getEmcTileEntities() {
		return emcTileEntities.values();
	}

	@Inject(method = "addBlockEntity", at = @At("TAIL"))
	public void addBlockEntity(BlockEntity blockEntity, CallbackInfoReturnable<Boolean> ci) {
		emcTileEntities.put(blockEntity, new ITileEntity(blockEntity));
	}

	@Inject(method = "tickBlockEntities", at = @At("HEAD"))
	private void tickBlockEntities(CallbackInfo info) {
		if (!unloadedBlockEntities.isEmpty()) {
			for (BlockEntity entity : unloadedBlockEntities) {
				emcTileEntities.remove(entity);
			}
		}
	}

	@Inject(method = "removeBlockEntity", at = @At("HEAD"))
	public void removeBlockEntity(BlockPos pos, CallbackInfo info) {
		BlockEntity blockEntity = this.getBlockEntity(pos);
		if (blockEntity != null) {
			emcTileEntities.remove(blockEntity);
		}
	}

}
