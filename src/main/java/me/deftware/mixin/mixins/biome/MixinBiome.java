package me.deftware.mixin.mixins.biome;

import me.deftware.client.framework.registry.BlockRegistry;
import me.deftware.client.framework.world.gen.BiomeDecorator;
import me.deftware.client.framework.world.gen.DecoratorConfig;
import me.deftware.client.framework.world.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.AbstractCountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Extracts all biome generation data
 */
@Mixin(BuiltinBiomes.class)
public class MixinBiome {

    @Inject(method = "register", at = @At("RETURN"))
    private static void onRegister(RegistryKey<Biome> key, Biome biome, CallbackInfo ci) {
        register(biome, key.getValue());
    }

    @Unique
    private static void register(Biome biome, Identifier identifier) {
        BiomeDecorator decorator = new BiomeDecorator();
        // System.out.printf("== Biome %s ==\n", identifier.getPath());
        parseFeature(biome.getGenerationSettings(), GenerationStep.Feature.UNDERGROUND_ORES, decorator);
        parseFeature(biome.getGenerationSettings(), GenerationStep.Feature.UNDERGROUND_DECORATION, decorator);
        BiomeDecorator.BIOME_DECORATORS.put(identifier, decorator);
    }

    @Unique
    private static void parseFeature(GenerationSettings generationSettings, GenerationStep.Feature biomeFeature, BiomeDecorator decorator) {
        if (biomeFeature.ordinal() >= generationSettings.getFeatures().size())
            return;
        RegistryEntryList<PlacedFeature> list = generationSettings.getFeatures().get(biomeFeature.ordinal());
        for (int i = 0; i < list.size(); i++) {
            PlacedFeature placedFeature = list.get(i).value();
            DecoratorConfig data = new DecoratorConfig(i, biomeFeature);
            BuiltinRegistries.PLACED_FEATURE.getKey(placedFeature).ifPresent(k -> data.setId(k.getValue().getPath()));
            for (PlacementModifier modifier : placedFeature.placementModifiers()) {
                // System.out.printf("\t[Placement modifier] %s\n", modifier.getClass().getSimpleName());
                if (modifier instanceof HeightRangePlacementModifier) {
                    data.setHeightProvider(((HeightPlacementAccessor) modifier).getHeight());
                } else if (modifier instanceof AbstractCountPlacementModifier) {
                    data.setRepeat((CountInvoker) modifier);
                } else if (modifier instanceof RarityFilterPlacementModifier) {
                    RarityAccessor accessor = (RarityAccessor) modifier;
                    data.setChance(accessor.getChance());
                }
            }
            List<FeatureConfig> configs = placedFeature.getDecoratedFeatures()
                    .map(ConfiguredFeature::config)
                    .collect(Collectors.toList());
            for (FeatureConfig config : configs) {
                if (config instanceof OreFeatureConfig oreFeatureConfig) {
                    data.setSize(oreFeatureConfig.size);
                    data.setDiscardOnAirChance(oreFeatureConfig.discardOnAirChance);
                    for (OreFeatureConfig.Target targets : oreFeatureConfig.targets) {
                        Block block = BlockRegistry.INSTANCE.getBlock(targets.state.getBlock());
                        data.getBlockList().add(block);
                    }
                }
            }
            List<Feature<?>> features = placedFeature.getDecoratedFeatures()
                            .map(ConfiguredFeature::feature)
                            .collect(Collectors.toList());
            for (Feature<?> feature : features) {
                if (feature instanceof ScatteredOreFeature) {
                    data.setFeatureType(DecoratorConfig.FeatureType.ScatteredOre);
                }
            }
            // System.out.println(data);
            decorator.getDecorators().add(data);
        }
    }

}
