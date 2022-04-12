package me.deftware.client.framework.world.gen;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.world.block.Block;
import me.deftware.client.framework.world.chunk.Randomizer;
import me.deftware.mixin.mixins.biome.CountInvoker;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;

import java.util.*;

/**
 * Represents Biome ore generation
 *
 * @author Deftware
 */
@Setter
@Getter
public class DecoratorConfig {

    /**
     * The feature registry id
     */
    private String id = "unknown";

    /**
     * Feature config index
     */
    private int index;

    /**
     * The min and max Y level the block can spawn in
     */
    private HeightProvider heightProvider;

    /**
     * How many times the block generation should be repeated
     */
    private CountInvoker repeat = (CountInvoker) CountPlacementModifier.of(1);

    /**
     * The size of a generation
     */
    private int size;

    /**
     * Rarity
     */
    private int chance = 1;

    /**
     * Specifies the chance a block will be discarded if it's spawned in the air
     */
    private float discardOnAirChance;

    /**
     * List of all blocks this decorator creates
     */
    private final Set<Block> blockList = new HashSet<>();

    /**
     * The structure of this decorator
     */
    private StructureType structureType = StructureType.Uniform;

    /**
     * The generation method
     */
    private FeatureType featureType = FeatureType.Ore;

    /**
     * The generation type
     */
    private final GenerationStep.Feature feature;

    public DecoratorConfig(int index, GenerationStep.Feature feature) {
        this.index = index;
        this.feature = feature;
    }

    public int getY(Randomizer random, DecoratorContext context) {
        return this.heightProvider.get((Random) random, context.getHeightContext());
    }

    public int getFeature() {
        return feature.ordinal();
    }

    public int getRepeat(Randomizer random) {
        return this.repeat.getInvokedCount((Random) random, null);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "[ ", " ]")
                .add("id=" + this.id)
                .add("index=" + this.index)
                .add("size=" + this.size)
                .add("heightType=" + this.structureType.name())
                .add("feature=" + this.feature.name())
                .add("generator=" + this.featureType.name())
                .add("chance=" + this.chance)
                .add("height=" + (this.heightProvider == null ? "Unknown" : this.heightProvider.toString()))
                .add("blocks=[" + String.join(",", this.blockList.stream().map(Block::getIdentifierKey).toArray(String[]::new)) + "]")
                .toString();
    }

    public enum StructureType {
        Uniform,
        Trapezoid
    }

    public enum FeatureType {
        Ore,
        ScatteredOre,
        Emerald
    }

}
