package me.deftware.mixin.mixins.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.AbstractCountPlacementModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(AbstractCountPlacementModifier.class)
public interface CountInvoker {

    @Invoker("getCount")
    int getInvokedCount(Random random, BlockPos pos);

}
