package me.deftware.mixin.mixins.biome;

import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HeightRangePlacementModifier.class)
public interface HeightPlacementAccessor {

    @Accessor("height")
    HeightProvider getHeight();

}
