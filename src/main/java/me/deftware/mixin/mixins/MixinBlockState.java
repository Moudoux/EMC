package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventBlockhardness;
import me.deftware.client.framework.event.events.EventCollideCheck;
import me.deftware.client.framework.event.events.EventVoxelShape;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.world.IBlock;
import net.minecraft.block.*;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
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

@Mixin(BlockState.class)
public abstract class MixinBlockState {
    @Shadow
    public abstract Block getBlock();

    @Shadow @Final
    private int luminance;

    @Shadow public abstract float getHardness(BlockView view, BlockPos pos);

    @Shadow public abstract VoxelShape getOutlineShape(BlockView view, BlockPos pos);

    @Inject(method = "getOutlineShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    public void getOutlineShape(BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1, CallbackInfoReturnable<VoxelShape> ci) {
        EventCollideCheck event = new EventCollideCheck(new IBlock(this.getBlock()));
        event.broadcast();
        if (event.updated) {
            if (event.canCollide) {
                ci.setReturnValue(VoxelShapes.empty());
            }
        } else {
            if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(this.getBlock()), "outline"))) {
                boolean doOutline = (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(this.getBlock()), "outline", false);
                if (!doOutline) {
                    ci.setReturnValue(VoxelShapes.empty());
                }
            }
        }
    }

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void renderTypeSet(CallbackInfoReturnable<BlockRenderType> cir) {
        if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(this.getBlock()), "render"))) {
            boolean doRender = (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(this.getBlock()), "render", false);
            if (!doRender) {
                cir.setReturnValue(BlockRenderType.INVISIBLE);
            }
        }
    }

    @Inject(method = "isTranslucent", at = @At("HEAD"), cancellable = true)
    public void getIsTranslucent(BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(this.getBlock()), "translucent"))) {
            boolean doRender = (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(this.getBlock()), "translucent", false);
            cir.setReturnValue(doRender);
        }
    }

    @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    public void getLuminance(CallbackInfoReturnable<Integer> callback) {
        callback.setReturnValue(
                (int) SettingsMap.getValue(Registry.BLOCK.getRawId(this.getBlock()), "lightValue", luminance));
    }

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    public void calcBlockBreakingDelta(PlayerEntity playerEntity_1, BlockView blockView_1, BlockPos blockPos_1, CallbackInfoReturnable<Float> ci) {
        float float_1 = this.getHardness(blockView_1, blockPos_1);
        EventBlockhardness event = new EventBlockhardness();
        event.broadcast();
        if (float_1 < 0.0F) {
            ci.setReturnValue(0.0F);
        } else {
            ci.setReturnValue(!playerEntity_1.isUsingEffectiveTool(this.getBlock().getDefaultState()) ? playerEntity_1.getBlockBreakingSpeed(this.getBlock().getDefaultState()) / float_1 / 100.0F
                    : playerEntity_1.getBlockBreakingSpeed(this.getBlock().getDefaultState()) / float_1 / 30.0F * event.getMultiplier());
        }
    }

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    public void getCollisionShape(BlockView blockView_1, BlockPos blockPos_1, EntityContext context, CallbackInfoReturnable<VoxelShape> ci) {
        EventVoxelShape event = new EventVoxelShape(this.getOutlineShape(blockView_1, blockPos_1), new IBlock(this.getBlock()));
        event.broadcast();
        if (event.modified) {
            ci.setReturnValue(event.shape);
        } else {
            if (this.getBlock() instanceof FluidBlock) {
                ci.setReturnValue((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "LIQUID_VOXEL_FULL", false)
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
