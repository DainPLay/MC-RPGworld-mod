package net.dainplay.rpgworldmod.world.feature;

import com.google.common.collect.ImmutableList;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static net.minecraft.data.worldgen.placement.VegetationPlacements.TREE_THRESHOLD;

public class ModPlacedFeatures {
    private static ImmutableList.Builder<PlacementModifier> onLandPlacementBase(PlacementModifier placement) {
        return ImmutableList.<PlacementModifier>builder()
                .add(placement)
                .add(InSquarePlacement.spread())
                .add(SurfaceWaterDepthFilter.forMaxDepth(0))
                .add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR)
                .add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
                .add(TREE_THRESHOLD)
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
    public static final Holder<PlacedFeature> RPGIROLLE_PLACED = PlacementUtils.register("rpgirolle_placed",
            ModConfiguredFeatures.RPGIROLLE, RarityFilter.onAverageOnceEvery(8),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> PROJECTRUFFLE_PLACED = PlacementUtils.register("projectruffle_placed",
            ModConfiguredFeatures.PROJECTRUFFLE, RarityFilter.onAverageOnceEvery(6),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> MASKONITE_PLACED = PlacementUtils.register("maskonite_placed", ModConfiguredFeatures.MASKONITE, CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, SurfaceWaterDepthFilter.forMaxDepth(0), BiomeFilter.biome());
    public static final Holder<PlacedFeature> RIE_BUSH = PlacementUtils.register("rie_bush", ModConfiguredFeatures.RIE_BUSH, onLandPlacement(PlacementUtils.countExtra(5, 0.1f, 1)));
    public static final Holder<PlacedFeature> RIE_TREE_CHECKED = PlacementUtils.register("rie_tree", ModConfiguredFeatures.RIE_TREE, PlacementUtils.filteredByBlockSurvival(ModBlocks.RIE_SAPLING.get()));
    public static final Holder<PlacedFeature> RIE_MOSS_PLACED = PlacementUtils.register("rie_moss_placed", ModConfiguredFeatures.PATCH_MOSS_RIE, worldSurfaceSquaredWithCount(25));
    public static List<PlacementModifier> worldSurfaceSquaredWithCount(int p_195475_) {
        return List.of(CountPlacement.of(p_195475_), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }
}