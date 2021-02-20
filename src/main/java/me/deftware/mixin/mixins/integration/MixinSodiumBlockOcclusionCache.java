package me.deftware.mixin.mixins.integration;

import me.deftware.mixin.shared.BlockManagement;
import me.jellysquid.mods.sodium.client.render.occlusion.BlockOcclusionCache;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes EMCs block rendering override compatible with the Sodium performance mod
 */
@SuppressWarnings("InvalidInjectorMethodSignature")
@Mixin(value = BlockOcclusionCache.class, remap = false)
public class MixinSodiumBlockOcclusionCache {

	@Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true, remap = false)
	public void shouldDrawSide(BlockState state, BlockView view, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> ci) {
		BlockManagement.shouldDrawSide(state, view, pos, facing, ci);
	}

}
