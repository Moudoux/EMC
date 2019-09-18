package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(BlockModelRenderer.class)
public abstract class MixinBlockModelRenderer {

    @Shadow
    protected abstract void renderQuad(float float_1, float float_2, float float_3, float float_4, List<BakedQuad> list_1);

    @Inject(method = "tesselate", at = @At("HEAD"), cancellable = true)
    public void tesselate(BlockRenderView extendedBlockView_1, BakedModel bakedModel_1, BlockState blockState_1, BlockPos blockPos_1, BufferBuilder bufferBuilder_1, boolean boolean_1, Random random_1, long long_1, CallbackInfoReturnable<Boolean> ci) {
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

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public void render(BlockState blockState_1, BakedModel bakedModel_1, float float_1, float float_2, float float_3, float float_4) {
        if (blockState_1 != null) {
            try {
                float_1 = (float) SettingsMap.getValue(Registry.BLOCK.getRawId(blockState_1.getBlock()),
                        "lightValue", float_1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        Random random_1 = new Random();
        long long_1 = 42L;
        Direction[] var10 = Direction.values();
        int var11 = var10.length;

        for (int var12 = 0; var12 < var11; ++var12) {
            Direction direction_1 = var10[var12];
            random_1.setSeed(42L);
            this.renderQuad(float_1, float_2, float_3, float_4, bakedModel_1.getQuads(blockState_1, direction_1, random_1));
        }

        random_1.setSeed(42L);
        this.renderQuad(float_1, float_2, float_3, float_4, bakedModel_1.getQuads(blockState_1, (Direction) null, random_1));
    }

}
