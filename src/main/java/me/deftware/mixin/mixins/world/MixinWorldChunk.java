package me.deftware.mixin.mixins.world;

import me.deftware.client.framework.world.classifier.BlockClassifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkRendererRegion.class)
public abstract class MixinWorldChunk {

	@Shadow @Final
	protected BlockState[] blockStates;

	@Shadow
	protected abstract int getIndex(BlockPos pos);

	/*
		This function is called every time a chunk is built
	 */
	@Inject(method = "getBlockState", at = @At("HEAD"))
	private void getBlockState(BlockPos pos, CallbackInfoReturnable<BlockState> state) {
		if (blockStates != null && pos != null) {
			BlockState blockState = blockStates[getIndex(pos)];
			Block block = blockState.getBlock();
			int id = Registry.BLOCK.getRawId(block);
			BlockClassifier.getClassifiers().forEach(blockClassifier -> blockClassifier.classify(block, pos, id));
		}
	}

}
