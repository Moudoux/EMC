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
import net.minecraftforge.client.model.data.IModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@SuppressWarnings("ALL")
@Mixin(BlockModelRenderer.class)
public abstract class MixinOptiFineBlockModelRenderer {

    @Inject(method = "renderModel", at = @At("HEAD"), cancellable = true)
    public void renderModel(BlockRenderView blockRenderView_1, BakedModel bakedModel_1, BlockState blockState_1, BlockPos blockPos_1, MatrixStack matrixStack_1, VertexConsumer vertexConsumer_1, boolean boolean_1, Random random_1, long long_1, int int_1, IModelData data, CallbackInfoReturnable<Boolean> ci) {
        boolean_1 = false;
        if (blockState_1.getBlock() instanceof FluidBlock) {
            ci.setReturnValue(((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "FLUIDS", true)));
        } else {
            if (SettingsMap.isOverrideMode() || (SettingsMap.isOverwriteMode() && SettingsMap.hasValue(Registry.BLOCK.getRawId(blockState_1.getBlock()), "render"))) {
                boolean doRender = (boolean) SettingsMap.getValue(Registry.BLOCK.getRawId(blockState_1.getBlock()), "render", false);
                if (!doRender) {
                    ci.setReturnValue(false);
                }
            }
        }
    }

    /*

    TODO: Fix these methods:

     @ModifyArg(method = {"renderModel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/BlockModelRenderer;tesselateFlat(Lnet/minecraft/world/ExtendedBlockView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/render/BufferBuilder;ZLjava/util/Random;J)Z"))
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

    @ModifyArg(method = {"renderModel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/BlockModelRenderer;tesselateSmooth(Lnet/minecraft/world/ExtendedBlockView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/render/BufferBuilder;ZLjava/util/Random;J)Z"))
    private boolean renderModelSmooth1(boolean checkSides) {
        try {
            if (SettingsMap.isOverrideMode()) {
                return false;
            }
        } catch (Exception exception) {}

        return checkSides;
    }

     */

}
