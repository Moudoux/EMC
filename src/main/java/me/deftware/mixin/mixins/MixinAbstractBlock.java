package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventBlockhardness;
import me.deftware.client.framework.event.events.EventCollideCheck;
import me.deftware.client.framework.event.events.EventVoxelShape;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.mixin.imp.IMixinAbstractBlock;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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

@Mixin(AbstractBlock.class)
public abstract class MixinAbstractBlock implements IMixinAbstractBlock {

    @Shadow
    @Final
    protected boolean collidable;

    @Shadow public abstract Item asItem();

    @Shadow @Final protected float slipperiness;

    @Shadow @Final protected float velocityMultiplier;

    @Inject(method = "getOutlineShape", at = @At("HEAD"), cancellable = true)
    public void getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> ci) {
        EventCollideCheck event = new EventCollideCheck(me.deftware.client.framework.world.block.Block.newInstance(state.getBlock()));
        event.broadcast();
        if (event.updated) {
            if (event.canCollide) {
                ci.setReturnValue(VoxelShapes.empty());
            }
        } else {
            if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(state.getBlock()), "outline"))) {
                boolean doOutline = (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(state.getBlock()), "outline", true);
                if (!doOutline) {
                    ci.setReturnValue(VoxelShapes.empty());
                }
            }
        }
    }

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void renderTypeSet(BlockState state, CallbackInfoReturnable<BlockRenderType> cir) {
        if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(state.getBlock()), "render"))) {
            boolean doRender = (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(state.getBlock()), "render", false);
            if (!doRender) {
                cir.setReturnValue(BlockRenderType.INVISIBLE);
            }
        }
    }

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    public void getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> ci) {
        EventVoxelShape event = new EventVoxelShape(collidable ? state.getOutlineShape(world, pos) : VoxelShapes.empty(), me.deftware.client.framework.world.block.Block.newInstance((Block) (Object) this));
        event.broadcast();
        if (event.modified) {
            ci.setReturnValue(event.shape);
        } else {
            if (Block.getBlockFromItem(this.asItem()) instanceof FluidBlock) {
                ci.setReturnValue((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "LIQUID_VOXEL_FULL", false)
                        ? VoxelShapes.fullCube()
                        : VoxelShapes.empty());
            } else if (Block.getBlockFromItem(this.asItem()) instanceof SweetBerryBushBlock) {
                if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "custom_berry_voxel", false)) {
                    ci.setReturnValue(VoxelShapes.fullCube());
                }
            }
        }
    }

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    public void calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> ci) {
        float float_1 = state.getHardness(world, pos);
        EventBlockhardness event = new EventBlockhardness();
        event.broadcast();
        if (float_1 < 0.0F) {
            ci.setReturnValue(0.0F);
        } else {
            ci.setReturnValue(!player.isUsingEffectiveTool(state) ? player.getBlockBreakingSpeed(state) / float_1 / 100.0F
                    : player.getBlockBreakingSpeed(state) / float_1 / 30.0F * event.getMultiplier());
        }
    }

    @Override
    public float getTheSlipperiness() {
        return this.slipperiness;
    }

    @Override
    public float getTheVelocityMultiplier() {
        return this.velocityMultiplier;
    }

}
