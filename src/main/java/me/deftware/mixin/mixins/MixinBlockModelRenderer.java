package me.deftware.mixin.mixins;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.deftware.client.framework.maps.SettingsMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

@Mixin(BlockModelRenderer.class)
public abstract class MixinBlockModelRenderer {

	@Shadow
	protected abstract void renderModelBrightnessColorQuads(float brightness, float red, float green, float blue,
			List<BakedQuad> listQuads);

	@Inject(method = "renderModel", at = @At("HEAD"), cancellable = true)
	private void renderModel(IWorldReader p_199324_1_, IBakedModel p_199324_2_, IBlockState p_199324_3_,
							   BlockPos p_199324_4_, BufferBuilder p_199324_5_, boolean p_199324_6_, Random p_199324_7_,
							   long p_199324_8_, CallbackInfoReturnable<Boolean> ci) {
		if (SettingsMap.isOverrideMode()) {
			if (!(boolean) SettingsMap.getValue(IRegistry.BLOCK.getId(p_199324_3_.getBlock()), "render", false)) {
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
		if (state != null) {
			try {
				p_187495_3_ = (float) SettingsMap.getValue(IRegistry.BLOCK.getId(state.getBlock()),
						"lightValue", p_187495_3_);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		Random random = new Random();
		long i = 42L;

		for (EnumFacing enumfacing : EnumFacing.values()) {
			random.setSeed(42L);
			this.renderModelBrightnessColorQuads(p_187495_3_, p_187495_4_, p_187495_5_, p_187495_6_,
					p_187495_2_.getQuads(state, enumfacing, random));
		}

		random.setSeed(42L);
		this.renderModelBrightnessColorQuads(p_187495_3_, p_187495_4_, p_187495_5_, p_187495_6_,
				p_187495_2_.getQuads(state, (EnumFacing) null, random));
	}

}
