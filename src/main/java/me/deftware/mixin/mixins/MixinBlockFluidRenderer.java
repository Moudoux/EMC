package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventRenderFluid;
import me.deftware.client.framework.wrappers.world.IBlock;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockFluidRenderer.class)
public class MixinBlockFluidRenderer {

	@Inject(method = "renderFluid", at = @At("HEAD"), cancellable = true)
	private void renderFluid(IBlockAccess blockAccess, IBlockState blockStateIn, BlockPos blockPosIn,
							 BufferBuilder worldRendererIn, CallbackInfoReturnable<Boolean> ci) {
		BlockLiquid blockliquid = (BlockLiquid) blockStateIn.getBlock();
		EventRenderFluid event = new EventRenderFluid(new IBlock(blockliquid)).send();
		if (event.isCanceled()) {
			ci.cancel();
		}
	}

}
