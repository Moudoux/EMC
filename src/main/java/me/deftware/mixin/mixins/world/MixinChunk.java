package me.deftware.mixin.mixins.world;

import me.deftware.client.framework.world.chunk.ChunkAccessor;
import me.deftware.client.framework.world.chunk.SectionAccessor;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldChunk.class)
public class MixinChunk implements ChunkAccessor {

    @Override
    public SectionAccessor getSection(int index) {
        return (SectionAccessor) ((WorldChunk) (Object) this).getSection(index);
    }

    @Override
    public int getChunkPosX() {
        return ((WorldChunk) (Object) this).getPos().x;
    }

    @Override
    public int getChunkPosZ() {
        return ((WorldChunk) (Object) this).getPos().z;
    }

    @Override
    public int getChunkHeight() {
        return ((WorldChunk) (Object) this).getHeight();
    }

}
