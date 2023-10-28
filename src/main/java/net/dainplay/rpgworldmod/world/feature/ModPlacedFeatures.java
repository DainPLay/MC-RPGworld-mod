package net.dainplay.rpgworldmod.world.feature;

import com.google.common.collect.ImmutableList;
import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    private static ImmutableList.Builder<PlacementModifier> onLandPlacementBase(PlacementModifier placement) {
        return ImmutableList.<PlacementModifier>builder()
                .add(placement)
                .add(InSquarePlacement.spread())
                .add(SurfaceWaterDepthFilter.forMaxDepth(0))
                .add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR)
                .add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
                .add(BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK), new BlockPos(0, -1, 0))))
                .add(BiomeFilter.biome());
    }

    private static List<PlacementModifier> onLandPlacement(PlacementModifier placement) {
        return onLandPlacementBase(placement).build();
    }
    public static final Holder<PlacedFeature> RIE_PLACED = PlacementUtils.register("rie_placed",
            ModConfiguredFeatures.RIE_SPAWN, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(2, 0.5f, 1)));
    public static final Holder<PlacedFeature> SHIVERALIS_PLACED = PlacementUtils.register("shiveralis_placed",
            ModConfiguredFeatures.SHIVERALIS, RarityFilter.onAverageOnceEvery(6),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> RIE_FLOWER_PLACED = PlacementUtils.register("rie_flower_placed",
            ModConfiguredFeatures.RIE_FLOWER, RarityFilter.onAverageOnceEvery(2147483647),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> PROJECTRUFFLE_PLACED = PlacementUtils.register("projectruffle_placed",
            ModConfiguredFeatures.PROJECTRUFFLE, RarityFilter.onAverageOnceEvery(6),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> HOLTS_REFLECTION_PLACED = PlacementUtils.register("holts_reflection_placed",
            ModConfiguredFeatures.HOLTS_REFLECTION, RarityFilter.onAverageOnceEvery(6),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> RPGIROLLE_PLACED = PlacementUtils.register("rpgirolle_placed",
            ModConfiguredFeatures.RPGIROLLE, RarityFilter.onAverageOnceEvery(8),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> SPIKY_IVY_PLACED = PlacementUtils.register("spiky_ivy_placed", ModConfiguredFeatures.SPIKY_IVY, CountPlacement.of(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, SurfaceWaterDepthFilter.forMaxDepth(0), BiomeFilter.biome(), RarityFilter.onAverageOnceEvery(4));
    public static final Holder<PlacedFeature> TREE_HOLLOW_WEST_PLACED = PlacementUtils.register("tree_hollow_west_placed", ModConfiguredFeatures.TREE_HOLLOW_WEST, CountPlacement.of(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, SurfaceWaterDepthFilter.forMaxDepth(0), BiomeFilter.biome());
    public static final Holder<PlacedFeature> TREE_HOLLOW_EAST_PLACED = PlacementUtils.register("tree_hollow_east_placed", ModConfiguredFeatures.TREE_HOLLOW_EAST, CountPlacement.of(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, SurfaceWaterDepthFilter.forMaxDepth(0), BiomeFilter.biome());
    public static final Holder<PlacedFeature> TREE_HOLLOW_SOUTH_PLACED = PlacementUtils.register("tree_hollow_south_placed", ModConfiguredFeatures.TREE_HOLLOW_SOUTH, CountPlacement.of(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, SurfaceWaterDepthFilter.forMaxDepth(0), BiomeFilter.biome());
    public static final Holder<PlacedFeature> TREE_HOLLOW_NORTH_PLACED = PlacementUtils.register("tree_hollow_north_placed", ModConfiguredFeatures.TREE_HOLLOW_NORTH, CountPlacement.of(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, SurfaceWaterDepthFilter.forMaxDepth(0), BiomeFilter.biome());
    public static final Holder<PlacedFeature> MASKONITE_PLACED = PlacementUtils.register("maskonite_placed", ModConfiguredFeatures.MASKONITE, CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, SurfaceWaterDepthFilter.forMaxDepth(0), BiomeFilter.biome());
    public static final Holder<PlacedFeature> RIE_BUSH = PlacementUtils.register("rie_bush", ModConfiguredFeatures.RIE_BUSH, onLandPlacement(PlacementUtils.countExtra(5, 0.1f, 1)));
    public static final Holder<PlacedFeature> RIE_TREE_CHECKED = PlacementUtils.register("rie_tree", ModConfiguredFeatures.RIE_TREE, PlacementUtils.filteredByBlockSurvival(ModBlocks.RIE_SAPLING.get()));
    public static final Holder<PlacedFeature> RIE_MOSS_PLACED = PlacementUtils.register("rie_moss_placed", ModConfiguredFeatures.PATCH_MOSS_RIE, worldSurfaceSquaredWithCount(25));

    public static List<PlacementModifier> worldSurfaceSquaredWithCount(int p_195475_) {
        return List.of(CountPlacement.of(p_195475_), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }
}