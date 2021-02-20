package me.deftware.mixin.mixins.block;

import me.deftware.client.framework.event.events.EventCollideCheck;
import me.deftware.client.framework.global.GameKeys;
import me.deftware.client.framework.global.GameMap;
import me.deftware.client.framework.global.types.BlockProperty;
import me.deftware.client.framework.global.types.PropertyManager;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.math.position.DoubleBlockPosition;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class MixinBlockState {

	@Shadow
	public abstract Block getBlock();

	@Shadow
	public abstract FluidState getFluidState();

	@Inject(method = "getOutlineShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
	public void getOutlineShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> ci) {
		EventCollideCheck event = new EventCollideCheck(
				me.deftware.client.framework.world.block.Block.newInstance(this.getBlock()),
				DoubleBlockPosition.fromMinecraftBlockPos(pos)
		).broadcast();
		if (event.updated) {
			if (event.canCollide) {
				ci.setReturnValue(VoxelShapes.empty());
			}
		}
	}

	@Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
	public void getLuminance(CallbackInfoReturnable<Integer> callback) {
		PropertyManager<BlockProperty> blockProperties = Bootstrap.blockProperties;
		if (blockProperties.isActive()) {
			int id = Registry.BLOCK.getRawId(this.getBlock());
			if (blockProperties.contains(id))
				callback.setReturnValue(blockProperties.get(id).getLuminance());
		}
	}

	@Inject(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
	public void getCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> ci) {
		if (!this.getFluidState().isEmpty()) {
			boolean fullCube = GameMap.INSTANCE.get(GameKeys.FULL_LIQUID_VOXEL, false);
			if (fullCube) {
				if (!(pos.getX() == MinecraftClient.getInstance().player.getBlockPos().getX() &&
						pos.getZ() == MinecraftClient.getInstance().player.getBlockPos().getZ())) {
					fullCube = false;
				}
			}
			if (fullCube) ci.setReturnValue(VoxelShapes.fullCube());
		} else if (this.getBlock() instanceof SweetBerryBushBlock && GameMap.INSTANCE.get(GameKeys.FULL_BERRY_VOXEL, false)) {
				ci.setReturnValue(VoxelShapes.fullCube());
		}
	}

}
