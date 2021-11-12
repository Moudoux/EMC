package me.deftware.mixin.mixins.biome;

import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Allows passing a null ChunkGenerator, and only
 * using the HeightViewLimit (Which Chunk implements)
 */
@Mixin(HeightContext.class)
public class MixinHeightContext {

    @Shadow
    @Final
    @Mutable
    private int minY;

    @Shadow
    @Final
    @Mutable
    private int height;

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;getMinimumY()I"))
    private int onGetChunkY(ChunkGenerator instance) {
        if (instance == null) {
            return -9999;
        }
        return instance.getMinimumY();
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;getWorldHeight()I"))
    private int onGetChunkHeight(ChunkGenerator instance) {
        if (instance == null) {
            return 9999;
        }
        return instance.getWorldHeight();
    }

}
