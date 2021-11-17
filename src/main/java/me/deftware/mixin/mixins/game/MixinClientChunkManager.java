package me.deftware.mixin.mixins.game;

import me.deftware.client.framework.world.chunk.BlockClassifier;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientChunkManager.class)
public abstract class MixinClientChunkManager {

	@Redirect(method = "unload", at = @At(target = "Lnet/minecraft/client/world/ClientChunkManager;positionEquals(Lnet/minecraft/world/chunk/WorldChunk;II)Z", value = "INVOKE", opcode = 180))
	private boolean positionEqualsRedirect(WorldChunk chunk, int x, int z) {
		if (chunk == null) {
			return false;
		} else {
			ChunkPos chunkPos = chunk.getPos();
			boolean match = chunkPos.x == x && chunkPos.z == z;
			if (match) {
				BlockClassifier.CLASSIFIERS.forEach(b -> b.unload(x, z));
			}
			return match;
		}
	}

}
