package me.deftware.client.framework.world.gen;

import lombok.Getter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class BiomeDecorator {

    @ApiStatus.Internal
    public static final Map<Identifier, BiomeDecorator> BIOME_DECORATORS = new HashMap<>();

    private final List<DecoratorConfig> decorators = new ArrayList<>();

}
