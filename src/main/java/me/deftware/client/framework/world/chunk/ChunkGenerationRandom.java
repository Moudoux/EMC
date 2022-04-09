package me.deftware.client.framework.world.chunk;

import net.minecraft.world.gen.random.AbstractRandom;
import net.minecraft.world.gen.random.ChunkRandom;

public interface ChunkGenerationRandom extends Randomizer {

    long _setPopulationSeed(long worldSeed, int blockX, int blockZ);

    void _setDecoratorSeed(long populationSeed, int index, int step);

    static ChunkGenerationRandom create(long seed) {
        AbstractRandom abRandom = ChunkRandom.RandomProvider.XOROSHIRO.create(seed);
        ChunkRandom chunkRandom = new ChunkRandom(abRandom);
        return (ChunkGenerationRandom) chunkRandom;
    }

}
