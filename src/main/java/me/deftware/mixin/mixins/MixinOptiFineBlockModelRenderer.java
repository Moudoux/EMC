package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraftforge.client.model.data.IModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/**
 *  OptiFine overwrites hooks used by EMC for block render override, so we need to inject into OptiFine and re-add those hooks
 *
 *  NOTE! This only works for OptiFine F5 and above
 *
 * @author Deftware
 */
@SuppressWarnings("ALL")
@Mixin(BlockModelRenderer.class)
public abstract class MixinOptiFineBlockModelRenderer {

    @Shadow(remap = false)
    public abstract boolean renderModelSmooth(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, IModelData data);

    @Shadow(remap = false)
    public abstract boolean renderModelFlat(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, IModelData data);

    @Inject(method = "renderModel", at = @At("HEAD"), cancellable = true)
    public void renderModel(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, IModelData data, CallbackInfoReturnable<Boolean> ci) {
        World.determineRenderState(state, pos, ci);
    }

    @Inject(method = "renderModelSmooth", at = @At("RETURN"), remap = false, cancellable = true)
    public void renderModelSmoothInject(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, IModelData data, CallbackInfoReturnable<Boolean> ci) {
        try {
            if (SettingsMap.isOverrideMode()) {
                if (cull) {
                    ci.setReturnValue(renderModelSmooth(world, model, state, pos, buffer, vertexConsumer, false, random, seed, overlay, data));
                }
            }
        } catch (Exception exception) {}
    }

    @Inject(method = "renderModelFlat", at = @At("HEAD"), remap = false, cancellable = true)
    public void renderModelFlatInject(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long l, int i, IModelData data, CallbackInfoReturnable<Boolean> ci) {
        try {
            if (SettingsMap.isOverrideMode()) {
                if (cull) {
                    ci.setReturnValue(renderModelFlat(world, model, state, pos, buffer, vertexConsumer, false, random, l, i, data));
                }
            }
        } catch (Exception exception) {}
    }

}
