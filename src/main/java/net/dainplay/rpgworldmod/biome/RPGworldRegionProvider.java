package net.dainplay.rpgworldmod.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;

import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class RPGworldRegionProvider extends Region {
    public static final ResourceLocation LOCATION = new ResourceLocation("minecraft:overworld");

    public RPGworldRegionProvider(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        this.addBiomeSimilar(mapper, Biomes.DARK_FOREST, BiomeInitializer.RIE_WEALD);
        this.addBiomeSimilar(mapper, Biomes.DESERT, Biomes.DESERT);
        this.addBiomeSimilar(mapper, Biomes.PLAINS, Biomes.PLAINS);
        this.addBiomeSimilar(mapper, Biomes.FOREST, Biomes.FOREST);
        this.addBiomeSimilar(mapper, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS);
        this.addBiomeSimilar(mapper, Biomes.OCEAN, Biomes.OCEAN);
        this.addBiomeSimilar(mapper, Biomes.COLD_OCEAN, Biomes.COLD_OCEAN);
        this.addBiomeSimilar(mapper, Biomes.WARM_OCEAN, Biomes.WARM_OCEAN);
        this.addBiomeSimilar(mapper, Biomes.RIVER, Biomes.RIVER);
    }
}
