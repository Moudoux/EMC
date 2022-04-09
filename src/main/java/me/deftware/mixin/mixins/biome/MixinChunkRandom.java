package me.deftware.mixin.mixins.biome;

import me.deftware.client.framework.world.chunk.ChunkGenerationRandom;
import net.minecraft.world.gen.random.ChunkRandom;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkRandom.class)
public abstract class MixinChunkRandom implements ChunkGenerationRandom {

    @Override
    public long _setPopulationSeed(long worldSeed, int blockX, int blockZ) {
        return ((ChunkRandom) (Object) this).setPopulationSeed(worldSeed, blockX, blockZ);
    }

    @Override
    public void _setDecoratorSeed(long populationSeed, int index, int step) {
        ((ChunkRandom) (Object) this).setDecoratorSeed(populationSeed, index, step);
    }

    @Override
    public int _nextInt(int bound) {
        return ((ChunkRandom) (Object) this).nextInt(bound);
    }

    @Override
    public float _nextFloat() {
        return ((ChunkRandom) (Object) this).nextFloat();
    }

    @Override
    public double _nextDouble() {
        return ((ChunkRandom) (Object) this).nextDouble();
    }

}
