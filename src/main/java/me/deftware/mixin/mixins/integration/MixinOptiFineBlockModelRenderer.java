package me.deftware.mixin.mixins.integration;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.global.types.BlockPropertyManager;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import net.minecraftforge.client.model.data.IModelData;
import net.optifine.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/**
 *  OptiFine overwrites hooks used by EMC for block render override, so we need to inject into OptiFine and re-add those hooks
 *
 *  NOTE! This only works for OptiFine F5 and above
 *  TODO: This Mixin is defunct in 1.16.2, and needs repairs to restore functionality
 *
 * @author Deftware
 */
@Pseudo
@SuppressWarnings({"ShadowTarget", "UnresolvedMixinReference"})
@Mixin(BlockModelRenderer.class)
public abstract class MixinOptiFineBlockModelRenderer {

    @Shadow(remap = false)
    public abstract boolean renderModelSmooth(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, IModelData data);

    @Shadow(remap = false)
    public abstract boolean renderModelFlat(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, IModelData data);

    @Inject(method = "render(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLjava/util/Random;JI)Z", at = @At("HEAD"), cancellable = true)
    public void renderModel(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack matrix, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, CallbackInfoReturnable<Boolean> cir) {
        BlockPropertyManager blockProperties = Bootstrap.blockProperties;
        if (blockProperties.isActive() && !blockProperties.isOpacityMode()) {
            int id = Registry.BLOCK.getRawId(state.getBlock());
            if (!(blockProperties.contains(id) && blockProperties.get(id).isRender()))
                cir.setReturnValue(false);
        }
    }

    @Inject(method = "renderModelSmooth", at = @At("RETURN"), remap = false, cancellable = true)
    public void renderModelSmoothInject(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack matrix, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, IModelData data, CallbackInfoReturnable<Boolean> ci) {
        FrameworkConstants.CAN_RENDER_SHADER = !Config.isShaders();
        try {
            if (Bootstrap.blockProperties.isActive()) {
                if (cull) {
                    ci.setReturnValue(renderModelSmooth(world, model, state, pos, matrix, vertexConsumer, false, random, seed, overlay, data));
                }
            }
        } catch (Exception exception) {}
    }

    @Inject(method = "renderModelFlat", at = @At("HEAD"), remap = false, cancellable = true)
    public void renderModelFlatInject(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long l, int i, IModelData data, CallbackInfoReturnable<Boolean> ci) {
        try {
            if (Bootstrap.blockProperties.isActive()) {
                if (cull) {
                    ci.setReturnValue(renderModelFlat(world, model, state, pos, buffer, vertexConsumer, false, random, l, i, data));
                }
            }
        } catch (Exception exception) {}
    }

}
