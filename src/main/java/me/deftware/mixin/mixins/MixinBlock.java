package me.deftware.mixin.mixins;

import net.minecraft.state.StateContainer;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.chunk.BlockStateContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.deftware.client.framework.event.events.EventBlockhardness;
import me.deftware.client.framework.event.events.EventCollideCheck;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.world.IBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(Block.class)
public abstract class MixinBlock {

	@Shadow
	@Final
	protected StateContainer<Block, IBlockState> blockState;

	@Shadow
	private int lightValue;

	@Shadow
	protected abstract boolean isCollidable();

	@Inject(method = "getLightValue", at = @At("HEAD"), cancellable = true)
	private void getLightValue(IBlockState state, CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(
				(int) SettingsMap.getValue(Block.REGISTRY.getIDForObject(state.getBlock()), "lightValue", lightValue));
	}

	@Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
	private static void shouldSideBeRendered(IBlockState blockState, IBlockReader blockReader, BlockPos pos, EnumFacing side,
									  CallbackInfoReturnable<Boolean> callback) {
		if (SettingsMap.isOverrideMode()) {
			callback.setReturnValue(
					(boolean) SettingsMap.getValue(Block.REGISTRY.getIDForObject(blockState.getBlock()), "render", false));
		}
	}

	@Inject(method = "isCollidable", at = @At("HEAD"), cancellable = true)
	private void isCollidable(IBlockState state, CallbackInfoReturnable<Boolean> ci) {
		EventCollideCheck event = new EventCollideCheck(new IBlock(state.getBlock()), isCollidable()).send();
		ci.setReturnValue(event.isCollidable());
	}

	@Inject(method = "getPlayerRelativeBlockHardness", at = @At("HEAD"), cancellable = true)
	public void getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, IBlockReader reader, BlockPos pos,
			CallbackInfoReturnable<Float> ci) {
		float f = state.getBlockHardness(reader, pos);
		EventBlockhardness event = new EventBlockhardness().send();
		if (f < 0.0F) {
			ci.setReturnValue(0.0F);
		} else {
			ci.setReturnValue(!player.canHarvestBlock(state) ? player.getDigSpeed(state) / f / 100.0F
					: player.getDigSpeed(state) / f / 30.0F * event.getMultiplier());
		}
	}

	@Inject(method = "getRenderLayer", at = @At("HEAD"), cancellable = true)
	private void getRenderLayer(CallbackInfoReturnable<BlockRenderLayer> ci) {
		if (SettingsMap.isOverrideMode()) {
			if ((boolean) SettingsMap.getValue(Block.REGISTRY.getIDForObject(blockState.getBlock()), "translucent", true)) {
				ci.setReturnValue(BlockRenderLayer.TRANSLUCENT);
			}
		}
	}

}
