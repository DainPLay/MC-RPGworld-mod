package net.dainplay.rpgworldmod.world.feature;

import com.google.common.collect.ImmutableList;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModConfiguredFeatures {
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> RIE_TREE = FeatureUtils.register("rie_tree", Feature.TREE, createRieTree().decorators(ImmutableList.of(TrunkVineDecorator.INSTANCE, LeaveVineDecorator.INSTANCE)).ignoreVines().build());
    private static TreeConfiguration.TreeConfigurationBuilder createRieTree() {
        return createStraightBlobTree(ModBlocks.RIE_LOG.get(), ModBlocks.RIE_LEAVES.get(), 4, 8, 0, 2);
    }
    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new StraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
    }

    public static final Holder<PlacedFeature> RIE_CHECKED = PlacementUtils.register("rie_checked", RIE_TREE,
            PlacementUtils.filteredByBlockSurvival(ModBlocks.RIE_SAPLING.get()));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> RIE_SPAWN =
            FeatureUtils.register("rie_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(RIE_CHECKED,
                            0.5F)), RIE_CHECKED));
    //public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TROVER =
    //        FeatureUtils.register("flower_trover", Feature.FLOWER,
      //              new RandomPatchConfiguration(32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
        //                    new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.TROVER.get())))));

    //public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SHIVERALIS =
      //      FeatureUtils.register("flower_shiveralis", Feature.FLOWER,
        //          new RandomPatchConfiguration(14, 4, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
          //              new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.SHIVERALIS.get())))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SHIVERALIS =
            FeatureUtils.register("flower_shiveralis", Feature.RANDOM_PATCH,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.SHIVERALIS.get().defaultBlockState().setValue(SweetBerryBushBlock.AGE,
                                    Integer.valueOf(3)))), List.of(Blocks.GRASS_BLOCK)));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> RPGIROLLE =
            FeatureUtils.register("flower_rpgirolle", Feature.FLOWER,
                    new RandomPatchConfiguration(8, 2, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.RPGIROLLE.get())))));
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PROJECTRUFFLE =
            FeatureUtils.register("flower_projectruffle", Feature.FLOWER,
                    new RandomPatchConfiguration(14, 2, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PROJECTRUFFLE.get())))));
    public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> MASKONITE = FeatureUtils.register("maskonite", Feature.FOREST_ROCK, new BlockStateConfiguration(ModBlocks.MASKONITE_BLOCK.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> RIE_BUSH = FeatureUtils.register("rie_bush", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.RIE_LOG.get()), new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.simple(ModBlocks.RIE_LEAVES.get()), new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2), new TwoLayersFeatureSize(0, 0, 0))).build());
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_MOSS_RIE = FeatureUtils.register("patch_moss_rie", Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.MOSS_CARPET.defaultBlockState(), 3).add(ModBlocks.WIDOWEED.get().defaultBlockState(), 1))), BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.allOf(BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.MOSSY_COBBLESTONE, ModBlocks.MASKONITE_BLOCK.get()), new BlockPos(0, -1, 0)))))));

}