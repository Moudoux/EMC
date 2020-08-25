package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSlowdown;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.mixin.imp.IMixinAbstractBlock;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class MixinBlock {

    @Shadow public abstract Item asItem();

    @Inject(method = "getSlipperiness", at = @At("TAIL"), cancellable = true)
    public void getSlipperiness(CallbackInfoReturnable<Float> cir) {
        if (((IMixinAbstractBlock) this).getTheSlipperiness() != 0.6f) {
            Block block = Block.getBlockFromItem(this.asItem());
            EventSlowdown event = null;
            if (block instanceof IceBlock || block.getTranslationKey().contains("blue_ice") || block.getTranslationKey().contains("packed_ice")) {
                event = new EventSlowdown(EventSlowdown.SlowdownType.Slipperiness, 0.6f);
            }
            if (event != null) {
                event.broadcast();
                if (event.isCanceled()) {
                    cir.setReturnValue(event.getMultiplier());
                }
            }
        }
    }

    @Inject(method = "getVelocityMultiplier", at = @At(value = "TAIL"), cancellable = true)
    private void onGetVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        if (((IMixinAbstractBlock) this).getTheVelocityMultiplier() != 1.0f) {
            Block block = Block.getBlockFromItem(this.asItem());
            EventSlowdown event = null;
            if (block instanceof HoneyBlock) {
                event = new EventSlowdown(EventSlowdown.SlowdownType.Honey);
            } else if (block instanceof SoulSandBlock) {
                event = new EventSlowdown(EventSlowdown.SlowdownType.Soulsand);
            }
            if (event != null) {
                event.broadcast();
                if (event.isCanceled()) {
                    cir.setReturnValue(event.getMultiplier());
                }
            }
        }
    }

    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> callback) {
        if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(state.getBlock()), "render"))) {
            callback.setReturnValue(
                    (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(state.getBlock()), "render", false));
        }
    }

    @Inject(method = "isTranslucent", at = @At("HEAD"), cancellable = true)
    public void getIsTranslucent(BlockState state, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(state.getBlock()), "translucent"))) {
            cir.setReturnValue(
                    (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(state.getBlock()), "translucent", false));
        }
    }

}
