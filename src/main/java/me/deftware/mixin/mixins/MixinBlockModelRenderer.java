package me.deftware.mixin.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

@Mixin(BlockModelRenderer.class)
public abstract class MixinBlockModelRenderer {

	@Shadow
	protected abstract void renderModelBrightnessColorQuads(float brightness, float red, float green, float blue,
			List<BakedQuad> listQuads);

	@Inject(method = "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;Z)Z", at = @At("HEAD"), cancellable = true)
	private void renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn,
			BlockPos blockPosIn, BufferBuilder buffer, boolean checkSides, CallbackInfoReturnable<Boolean> ci) {
		if (SettingsMap.isOverrideMode()) {
			if (!(boolean) SettingsMap.getValue(Block.getIdFromBlock(blockStateIn.getBlock()), "render", false)) {
				ci.setReturnValue(false);
			}
		}
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public void renderModelBrightnessColor(IBlockState state, IBakedModel p_187495_2_, float p_187495_3_,
			float p_187495_4_, float p_187495_5_, float p_187495_6_) {
		float bVal = p_187495_3_;
		try {
			bVal = (int) SettingsMap.getValue(Block.getIdFromBlock(state.getBlock()), "lightValue", p_187495_3_);
		} catch (Exception ex) {
			bVal = p_187495_3_;
		}
		for (EnumFacing enumfacing : EnumFacing.values()) {
			renderModelBrightnessColorQuads(bVal, p_187495_4_, p_187495_5_, p_187495_6_,
					p_187495_2_.getQuads(state, enumfacing, 0L));
		}
		renderModelBrightnessColorQuads(bVal, p_187495_4_, p_187495_5_, p_187495_6_,
				p_187495_2_.getQuads(state, (EnumFacing) null, 0L));
	}

}
