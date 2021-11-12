package me.deftware.client.framework.world.gen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.HeightContext;
import org.jetbrains.annotations.ApiStatus;

public interface DecoratorContext {

    @ApiStatus.Internal
    HeightContext getHeightContext();

    static DecoratorContext create() {
        Chunk chunk = MinecraftClient.getInstance().world.getChunk(MinecraftClient.getInstance().player.getBlockPos());
        HeightContext heightContext = new HeightContext(null, chunk);
        return () -> heightContext;
    }

}
