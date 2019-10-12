package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventBlockhardness;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.block.*;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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

@Mixin(Block.class)
public abstract class MixinBlock {

    @Shadow
    @Final
    protected StateManager<Block, BlockState> stateFactory;

    @Shadow
    @Final
    private int lightLevel;

    /* TODO: Removed in 1.14?
    @Shadow
    protected abstract boolean isCollidable();
    */

    /* TODO: Removed in 1.14?
    @Inject(method = "isCollidable", at = @At("HEAD"), cancellable = true)
    private void isCollidable(IBlockState state, CallbackInfoReturnable<Boolean> ci) {
        EventCollideCheck event = new EventCollideCheck(new IBlock(state.getBlock()), isCollidable()).send();
        ci.setReturnValue(event.isCollidable());
    }
    */

    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void shouldDrawSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1, CallbackInfoReturnable<Boolean> callback) {
        if (SettingsMap.isOverrideMode()) {
            callback.setReturnValue(
                    (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(blockState_1.getBlock()), "render", false));
        }
    }

    @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    public void getLuminance(BlockState blockState_1, CallbackInfoReturnable<Integer> callback) {
        callback.setReturnValue(
                (int) SettingsMap.getValue(Registry.BLOCK.getRawId(blockState_1.getBlock()), "lightValue", lightLevel));
    }

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    public void calcBlockBreakingDelta(BlockState blockState_1, PlayerEntity playerEntity_1, BlockView blockView_1, BlockPos blockPos_1, CallbackInfoReturnable<Float> ci) {
        float float_1 = blockState_1.getHardness(blockView_1, blockPos_1);
        EventBlockhardness event = new EventBlockhardness();
        event.broadcast();
        if (float_1 < 0.0F) {
            ci.setReturnValue(0.0F);
        } else {
            ci.setReturnValue(!playerEntity_1.isUsingEffectiveTool(blockState_1) ? playerEntity_1.getBlockBreakingSpeed(blockState_1) / float_1 / 100.0F
                    : playerEntity_1.getBlockBreakingSpeed(blockState_1) / float_1 / 30.0F * event.getMultiplier());
        }
    }

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    public void getCollisionShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext context, CallbackInfoReturnable<VoxelShape> ci) {
        if ((Object) this instanceof FluidBlock) {
            ci.setReturnValue((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "LIQUID_VOXEL_FULL", false)
                    ? VoxelShapes.fullCube()
                    : VoxelShapes.empty());
        } else if ((Object) this instanceof SweetBerryBushBlock) {
            if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "custom_berry_voxel", false)) {
                ci.setReturnValue(VoxelShapes.fullCube());
            }
        }
    }

}
