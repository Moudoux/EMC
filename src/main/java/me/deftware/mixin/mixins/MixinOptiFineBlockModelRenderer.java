package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@SuppressWarnings("ALL")
@Mixin(BlockModelRenderer.class)
public abstract class MixinOptiFineBlockModelRenderer {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void tesselate(BlockRenderView blockRenderView_1, BakedModel bakedModel_1, BlockState blockState_1, BlockPos blockPos_1, MatrixStack matrixStack_1, VertexConsumer vertexConsumer_1, boolean boolean_1, Random random_1, long long_1, int int_1, CallbackInfoReturnable<Boolean> ci) {
        if (blockState_1.getBlock() instanceof FluidBlock) {
            ci.setReturnValue(((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "FLUIDS", true)));
        } else {
            if (SettingsMap.isOverrideMode()) {
                if (!(boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(blockState_1.getBlock()), "render", false)) {
                    ci.setReturnValue(false);
                }
            }
        }
    }

    @ModifyArg(method = {"renderFlat"}, at = @At(value = "INVOKE", target = "net/minecraft/client/render/block/BlockModelRenderer.renderFlat(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLjava/util/Random;JI)Z"))
    private boolean renderModelFlat1(boolean checkSides) {
        try {
            if (SettingsMap.isOverrideMode()) {
                if (SettingsMap.isOverrideMode()) {
                    return false;
                }
            }
        } catch (Exception exception) {}

        return checkSides;
    }

    @ModifyArg(method = {"renderSmooth"}, at = @At(value = "INVOKE", target = "net/minecraft/client/render/block/BlockModelRenderer.renderSmooth(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLjava/util/Random;JI)Z"))
    private boolean renderModelSmooth1(boolean checkSides) {
        try {
            if (SettingsMap.isOverrideMode()) {
                return false;
            }
        } catch (Exception exception) {}

        return checkSides;
    }

}
