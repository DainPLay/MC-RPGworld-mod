package net.dainplay.rpgworldmod.biome;

import net.dainplay.rpgworldmod.features.FancyOakTreeFeature;
import net.dainplay.rpgworldmod.util.ColorConstants;
import net.dainplay.rpgworldmod.world.feature.ModPlacedFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class RPGworldBiomeDecorator {

    private static int getSkyColorWithTemperatureModifier(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.6325F - f * 0.1F, 0.44F + f * 0.11F, 1F);
    }

    private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder)
    {
        return biome(precipitation, category, temperature, downfall, ColorConstants.STANDARD_WATER, ColorConstants.STANDARD_WATERFOG, ColorConstants.RIE_WEALD_FOLIAGE_COLOR, ColorConstants.RIE_WEALD_GRASS_COLOR, spawnBuilder, biomeBuilder);
    }

    private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall, int waterColor, int waterFogColor, int grassColor, int foliageColor, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder)
    {
        return (new Biome.BiomeBuilder())
                .precipitation(precipitation)
                .biomeCategory(category)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(waterColor)
                        .waterFogColor(waterFogColor)
                        .fogColor(12638463)
                        .skyColor(getSkyColorWithTemperatureModifier(temperature))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .foliageColorOverride(foliageColor)
                        .grassColorOverride(grassColor)
                        .build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build())
                .build();
    }

    public static Biome decorateRieWeald() {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeFeatures = new BiomeGenerationSettings.Builder();

        spawnSettings.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 3));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 5, 2, 6));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 80, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 95, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 90, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 85, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 100, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 7, 1, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));
        spawnSettings.addSpawn(MobCategory.AXOLOTLS, new MobSpawnSettings.SpawnerData(EntityType.AXOLOTL, 30, 1, 3));

        // I really now hate feature cycle in 1.18... is there anything providing a feature cycle documentation?

        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeFeatures);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeFeatures);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeFeatures);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeFeatures);
        BiomeDefaultFeatures.addDefaultSprings(biomeFeatures);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeFeatures);

        BiomeDefaultFeatures.addDefaultOres(biomeFeatures);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeFeatures);

        //BiomeDefaultFeatures.addForestFlowers(biomeFeatures);
       //BiomeDefaultFeatures.addWaterTrees(biomeFeatures);
        //BiomeDefaultFeatures.addGroveTrees(biomeFeatures);

        //BiomeDefaultFeatures.addFerns(biomeFeatures);

        //BiomeDefaultFeatures.addMossyStoneBlock(biomeFeatures);
        BiomeDefaultFeatures.addJungleVines(biomeFeatures);

        //BiomeDefaultFeatures.addMushroomFieldVegetation(biomeFeatures);
        //BiomeDefaultFeatures.addMeadowVegetation(biomeFeatures);

        //BiomeDefaultFeatures.addDefaultMushrooms(biomeFeatures);
        //BiomeDefaultFeatures.addDefaultExtraVegetation(biomeFeatures);
        //BiomeDefaultFeatures.addCommonBerryBushes(biomeFeatures);
        //BiomeDefaultFeatures.addJungleMelons(biomeFeatures);

        FancyOakTreeFeature.addFancyOakTrees(biomeFeatures);
        biomeFeatures.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatures.RIE_TREE_CHECKED);
        biomeFeatures.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatures.RIE_BUSH);
        biomeFeatures.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatures.MASKONITE_PLACED);
        biomeFeatures.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHIVERALIS_PLACED);
        biomeFeatures.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.PROJECTRUFFLE_PLACED);
        biomeFeatures.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.HOLTS_REFLECTION_PLACED);
        biomeFeatures.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.RPGIROLLE_PLACED);
        biomeFeatures.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.RIE_MOSS_PLACED);

        return biome(Biome.Precipitation.NONE, Biome.BiomeCategory.FOREST, 0.7F, 0.7F, ColorConstants.STANDARD_WATER, ColorConstants.STANDARD_WATERFOG, ColorConstants.RIE_WEALD_FOLIAGE_COLOR, ColorConstants.RIE_WEALD_GRASS_COLOR, spawnSettings, biomeFeatures);
    }
}
