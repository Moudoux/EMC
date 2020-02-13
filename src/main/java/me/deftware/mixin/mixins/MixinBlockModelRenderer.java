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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BlockModelRenderer.class)
public abstract class MixinBlockModelRenderer {

    @Inject(method = "render(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLjava/util/Random;JI)Z", at = @At("HEAD"), cancellable = true)
    public void render(BlockRenderView blockRenderView_1, BakedModel bakedModel_1, BlockState blockState_1, BlockPos blockPos_1, MatrixStack matrixStack_1, VertexConsumer vertexConsumer_1, boolean boolean_1, Random random_1, long long_1, int int_1, CallbackInfoReturnable<Boolean> ci) {
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
    @ModifyArgs(method = "render", at = @At("HEAD"))
    public void render(Args args, Matrix4f matrix4f_1, Matrix3f matrix3f_1, VertexConsumer vertexConsumer_1, BlockState blockState_1, BakedModel bakedModel_1, float float_1, float float_2, float float_3, int int_1, int int_2) {
        float newLight = float_1;
        if (blockState_1 != null) {
            try {
                newLight = (float) SettingsMap.getValue(Registry.BLOCK.getRawId(blockState_1.getBlock()),
                        "lightValue", float_1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        args.set(5, newLight);
    }*/

}
