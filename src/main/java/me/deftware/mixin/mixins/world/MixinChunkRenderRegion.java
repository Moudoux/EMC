package me.deftware.mixin.mixins.world;

import me.deftware.client.framework.world.chunk.BlockClassifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkRendererRegion.class)
public abstract class MixinChunkRenderRegion {

	@Shadow
	@Final
	protected int chunkZOffset;

	@Shadow
	@Final
	protected int chunkXOffset;

	@Shadow
	@Final
	protected WorldChunk[][] chunks;

	/*
		This function is called every time a chunk is built
	*/
	@Inject(method = "getBlockState", at = @At("HEAD"))
	private void getBlockState(BlockPos pos, CallbackInfoReturnable<BlockState> state) {
		if (pos != null) {
			int i = ChunkSectionPos.getSectionCoord(pos.getX()) - this.chunkXOffset;
			int j = ChunkSectionPos.getSectionCoord(pos.getZ()) - this.chunkZOffset;
			BlockState blockState = this.chunks[i][j].getBlockState(pos);
			if (blockState != null) {
				Block block = blockState.getBlock();
				String id = Registry.BLOCK.getId(block).getPath();
				long position = pos.asLong();
				BlockClassifier.CLASSIFIERS.forEach(blockClassifier -> {
					if (blockClassifier.isActive())
						blockClassifier.classify(position, id);
				});
			}
		}
	}

}
