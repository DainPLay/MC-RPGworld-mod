package net.dainplay.rpgworldmod.biome;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BiomeInitializer {
    public static final ResourceKey<Biome> RIE_WEALD = register("rie_weald");

    private static ResourceKey<Biome> register(String name)
    {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(RPGworldMod.MOD_ID, name));
    }
}