package me.deftware.mixin.mixins.block;

import me.deftware.client.framework.event.events.EventBlockhardness;
import me.deftware.client.framework.event.events.EventCollideCheck;
import me.deftware.client.framework.event.events.EventVoxelShape;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class MixinBlockState {

    @Shadow
    public abstract Block getBlock();

    @Shadow @Final
    private int luminance;

    @Shadow
    public abstract float getHardness(BlockView view, BlockPos pos);

    @Shadow
    public abstract VoxelShape getOutlineShape(BlockView view, BlockPos pos);

    @Shadow
    public abstract FluidState getFluidState();

    @Inject(method = "getOutlineShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    public void getOutlineShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> ci) {
        EventCollideCheck event = new EventCollideCheck(me.deftware.client.framework.world.block.Block.newInstance(this.getBlock()));
        event.broadcast();
        if (event.updated) {
            if (event.canCollide) {
                ci.setReturnValue(VoxelShapes.empty());
            }
        } else {
            if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(this.getBlock()), "outline"))) {
                boolean doOutline = (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(this.getBlock()), "outline", true);
                if (!doOutline) {
                    ci.setReturnValue(VoxelShapes.empty());
                }
            }
        }
    }

    @Inject(method = "isTranslucent", at = @At("HEAD"), cancellable = true)
    public void getIsTranslucent(BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(this.getBlock()), "translucent"))) {
            cir.setReturnValue(
                    (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(this.getBlock()), "translucent", false));
        }
    }

    @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    public void getLuminance(CallbackInfoReturnable<Integer> callback) {
        callback.setReturnValue(
                (int) SettingsMap.getValue(Registry.BLOCK.getRawId(this.getBlock()), "lightValue", luminance));
    }

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    public void calcBlockBreakingDelta(PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> ci) {
        float float_1 = this.getHardness(world, pos);
        EventBlockhardness event = new EventBlockhardness();
        event.broadcast();
        if (float_1 < 0.0F) {
            ci.setReturnValue(0.0F);
        } else {
            ci.setReturnValue(!player.isUsingEffectiveTool(this.getBlock().getDefaultState()) ? player.getBlockBreakingSpeed(this.getBlock().getDefaultState()) / float_1 / 100.0F
                    : player.getBlockBreakingSpeed(this.getBlock().getDefaultState()) / float_1 / 30.0F * event.getMultiplier());
        }
    }

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    public void getCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> ci) {
        EventVoxelShape event = new EventVoxelShape(this.getOutlineShape(world, pos), me.deftware.client.framework.world.block.Block.newInstance(this.getBlock()));
        event.broadcast();
        if (event.modified) {
            ci.setReturnValue(event.shape);
        } else {
            if (!this.getFluidState().isEmpty()) {
                boolean fullCube = (boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "LIQUID_VOXEL_FULL", false);
                if (fullCube) {
                    if (!(pos.getX() == MinecraftClient.getInstance().player.getBlockPos().getX() &&
                            pos.getZ() == MinecraftClient.getInstance().player.getBlockPos().getZ())) {
                        fullCube = false;
                    }
                }
                ci.setReturnValue(fullCube
                        ? VoxelShapes.fullCube()
                        : VoxelShapes.empty());
            } else if (this.getBlock() instanceof SweetBerryBushBlock) {
                if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "custom_berry_voxel", false)) {
                    ci.setReturnValue(VoxelShapes.fullCube());
                }
            }
        }
    }

}
