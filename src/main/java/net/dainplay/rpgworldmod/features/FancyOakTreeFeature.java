package net.dainplay.rpgworldmod.features;

import com.google.common.collect.ImmutableList;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.BiConsumer;

import static net.dainplay.rpgworldmod.block.custom.TreeHollowBlock.FACING;
import static net.minecraft.data.worldgen.placement.VegetationPlacements.TREE_THRESHOLD;

public class FancyOakTreeFeature {
    private static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier placement) {
        return ImmutableList.<PlacementModifier>builder()
                .add(placement)
                .add(InSquarePlacement.spread())
                .add(SurfaceWaterDepthFilter.forMaxDepth(0))
                .add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR)
                .add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
                .add(BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK), new BlockPos(0, -1, 0))))
                .add(BiomeFilter.biome());
    }

    private static List<PlacementModifier> treePlacement(PlacementModifier placement) {
        return treePlacementBase(placement).build();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createFancyOak() {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.RIE_LOG.get()), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(ModBlocks.RIE_LEAVES.get()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).decorators(ImmutableList.of(TrunkVineDecorator.INSTANCE, LeaveVineDecorator.INSTANCE)).ignoreVines();
    }

    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> FANCY_OAK_TREES = FeatureUtils.register("trees_fancy_oak", Feature.TREE, createFancyOak().build());

    static final Holder<PlacedFeature> FANCY_OAK_TREES_FEATURE = PlacementUtils.register("trees_fancy_oak_feature", FancyOakTreeFeature.FANCY_OAK_TREES, treePlacement(PlacementUtils.countExtra(5, 0.1f, 1)));

    public static void addFancyOakTrees(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FancyOakTreeFeature.FANCY_OAK_TREES_FEATURE);
    }
}
