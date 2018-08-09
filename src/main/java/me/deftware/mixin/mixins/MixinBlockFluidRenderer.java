package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventRenderFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockFluidRenderer.class)
public class MixinBlockFluidRenderer {

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void render(IWorldReader worldReader, BlockPos blockPos, BufferBuilder bufferBuilder, IFluidState fluidState, CallbackInfoReturnable<Boolean> ci) {
		boolean flag = fluidState.isTagged(FluidTags.LAVA);
		EventRenderFluid event = new EventRenderFluid(flag ? EventRenderFluid.FluidType.LAVA : EventRenderFluid.FluidType.WATER).send();
		if (event.isCanceled()) {
			ci.cancel();
		}
	}

}
