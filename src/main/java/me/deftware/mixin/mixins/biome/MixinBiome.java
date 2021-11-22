package me.deftware.mixin.mixins.biome;

import me.deftware.client.framework.registry.BlockRegistry;
import me.deftware.client.framework.world.gen.BiomeDecorator;
import me.deftware.client.framework.world.gen.DecoratorConfig;
import me.deftware.client.framework.world.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Extracts all biome generation data
 */
@Mixin(BuiltinBiomes.class)
public class MixinBiome {

    @Inject(method = "register", at = @At("RETURN"))
    private static void onRegister(RegistryKey<Biome> registryKey, Biome biome, CallbackInfoReturnable<Biome> cir) {
        register(biome, registryKey.getValue());
    }

    @Unique
    private static void register(Biome biome, Identifier identifier) {
        BiomeDecorator decorator = new BiomeDecorator();
        parseFeature(biome.getGenerationSettings(), GenerationStep.Feature.UNDERGROUND_ORES, decorator);
        parseFeature(biome.getGenerationSettings(), GenerationStep.Feature.UNDERGROUND_DECORATION, decorator);
        BiomeDecorator.BIOME_DECORATORS.put(identifier, decorator);
    }

    @Unique
    private static void parseFeature(GenerationSettings generationSettings, GenerationStep.Feature biomeFeature, BiomeDecorator decorator) {
        if (biomeFeature.ordinal() >= generationSettings.getFeatures().size())
            return;
        List<Supplier<PlacedFeature>> list = generationSettings.getFeatures().get(biomeFeature.ordinal());
        for (int i = 0; i < list.size(); i++) {
            PlacedFeature placedFeature = list.get(i).get();
            DecoratorConfig data = new DecoratorConfig(i, biomeFeature);
            BuiltinRegistries.PLACED_FEATURE.getKey(placedFeature).ifPresent(k -> data.setId(k.getValue().getPath()));
            for (PlacementModifier modifier : placedFeature.getPlacementModifiers()) {
                // System.out.printf("\t[Placement modifier] %s\n", modifier.getClass().getSimpleName());
                if (modifier instanceof HeightRangePlacementModifier) {
                    data.setHeightProvider(((HeightPlacementAccessor) modifier).getHeight());
                } else if (modifier instanceof AbstractCountPlacementModifier) {
                    data.setRepeat((CountInvoker) modifier);
                }
            }
            List<FeatureConfig> configs = placedFeature.getDecoratedFeatures()
                    .map(ConfiguredFeature::getConfig)
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
                            .map(ConfiguredFeature::getFeature)
                            .collect(Collectors.toList());
            for (Feature<?> feature : features) {
                if (feature instanceof ScatteredOreFeature) {
                    data.setFeatureType(DecoratorConfig.FeatureType.ScatteredOre);
                }
            }
            decorator.getDecorators().add(data);
        }
    }

}
