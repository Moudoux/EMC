package me.deftware.mixin.mixins.game;

import me.deftware.client.framework.world.classifier.BlockClassifier;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientChunkManager.class)
public abstract class MixinClientChunkManager {

	@Redirect(method = "unload", at = @At(target = "Lnet/minecraft/client/world/ClientChunkManager;positionEquals(Lnet/minecraft/world/chunk/WorldChunk;II)Z", value = "INVOKE", opcode = 180))
	private boolean positionEqualsRedirect(WorldChunk chunk, int x, int y) {
		if (chunk == null) {
			return false;
		} else {
			ChunkPos chunkPos = chunk.getPos();
			boolean match = chunkPos.x == x && chunkPos.z == y;
			if (match) {
				BlockClassifier.clear(chunkPos);
			}
			return match;
		}
	}

}
