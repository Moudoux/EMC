package me.deftware.mixin.mixins.biome;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlacedFeature.class)
public interface PlacedFeatureAccessor {

    @Accessor
    RegistryEntry<ConfiguredFeature<?, ?>> getFeature();

}
