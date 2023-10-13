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
    }
}
