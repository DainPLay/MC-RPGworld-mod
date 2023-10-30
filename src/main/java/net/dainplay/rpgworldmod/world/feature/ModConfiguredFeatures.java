package net.dainplay.rpgworldmod.world.feature;

import com.google.common.collect.ImmutableList;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.dainplay.rpgworldmod.block.custom.HoltsReflectionBlock;
import net.dainplay.rpgworldmod.features.SpikyIvyFeature;
import net.dainplay.rpgworldmod.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.sounds.Music;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
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
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.Random;

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
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SHIVERALIS =
            FeatureUtils.register("flower_shiveralis", Feature.RANDOM_PATCH,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.SHIVERALIS.get().defaultBlockState().setValue(SweetBerryBushBlock.AGE,
                                    Integer.valueOf(3)))), List.of(Blocks.GRASS_BLOCK)));
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FAIRAPIER =
            FeatureUtils.register("flower_fairapier", Feature.RANDOM_PATCH,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.WILD_FAIRAPIER.get().defaultBlockState())), List.of(Blocks.GRASS_BLOCK)));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> RIE_FLOWER =
            FeatureUtils.register("rie_flower", Feature.FLOWER,
                    new RandomPatchConfiguration(96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                            .add(ModBlocks.RPGIROLLE.get().defaultBlockState(), 8)
                            .add(ModBlocks.PROJECTRUFFLE.get().defaultBlockState(), 8)
                            .add(ModBlocks.WILD_FAIRAPIER.get().defaultBlockState(), 8)
                            .add(ModBlocks.HOLTS_REFLECTION.get().defaultBlockState().setValue(HoltsReflectionBlock.FACING, Direction.NORTH), 1)
                            .add(ModBlocks.HOLTS_REFLECTION.get().defaultBlockState().setValue(HoltsReflectionBlock.FACING, Direction.SOUTH), 1)
                            .add(ModBlocks.HOLTS_REFLECTION.get().defaultBlockState().setValue(HoltsReflectionBlock.FACING, Direction.EAST), 1)
                            .add(ModBlocks.HOLTS_REFLECTION.get().defaultBlockState().setValue(HoltsReflectionBlock.FACING, Direction.WEST), 1))))));
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> RPGIROLLE =
            FeatureUtils.register("flower_rpgirolle", Feature.NO_BONEMEAL_FLOWER,
                    new RandomPatchConfiguration(8, 2, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.RPGIROLLE.get())))));
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PROJECTRUFFLE =
            FeatureUtils.register("flower_projectruffle", Feature.NO_BONEMEAL_FLOWER,
                    new RandomPatchConfiguration(14, 3, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PROJECTRUFFLE.get())))));
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> HOLTS_REFLECTION =
            FeatureUtils.register("flower_holts_reflection", Feature.NO_BONEMEAL_FLOWER,
                    new RandomPatchConfiguration(10, 0, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                    .add(ModBlocks.HOLTS_REFLECTION.get().defaultBlockState().setValue(HoltsReflectionBlock.FACING, Direction.NORTH), 1)
                                    .add(ModBlocks.HOLTS_REFLECTION.get().defaultBlockState().setValue(HoltsReflectionBlock.FACING, Direction.SOUTH), 1)
                                    .add(ModBlocks.HOLTS_REFLECTION.get().defaultBlockState().setValue(HoltsReflectionBlock.FACING, Direction.EAST), 1)
                                    .add(ModBlocks.HOLTS_REFLECTION.get().defaultBlockState().setValue(HoltsReflectionBlock.FACING, Direction.WEST), 1))))));

    public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> MASKONITE = FeatureUtils.register("maskonite", Feature.FOREST_ROCK, new BlockStateConfiguration(ModBlocks.MASKONITE_BLOCK.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> RIE_BUSH = FeatureUtils.register("rie_bush", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.RIE_LOG.get()), new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.simple(ModBlocks.RIE_LEAVES.get()), new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2), new TwoLayersFeatureSize(0, 0, 0))).build());
    public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> SPIKY_IVY = FeatureUtils.register("spiky_ivy", RPGFeatures.SPIKY_IVY, new BlockStateConfiguration(ModBlocks.SPIKY_IVY.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> TREE_HOLLOW_WEST = FeatureUtils.register("tree_hollow_west", RPGFeatures.TREE_HOLLOW_WEST, new BlockStateConfiguration(ModBlocks.RIE_HOLLOW.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> TREE_HOLLOW_EAST = FeatureUtils.register("tree_hollow_east", RPGFeatures.TREE_HOLLOW_EAST, new BlockStateConfiguration(ModBlocks.RIE_HOLLOW.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> TREE_HOLLOW_NORTH = FeatureUtils.register("tree_hollow_north", RPGFeatures.TREE_HOLLOW_NORTH, new BlockStateConfiguration(ModBlocks.RIE_HOLLOW.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<BlockStateConfiguration, ?>> TREE_HOLLOW_SOUTH = FeatureUtils.register("tree_hollow_south", RPGFeatures.TREE_HOLLOW_SOUTH, new BlockStateConfiguration(ModBlocks.RIE_HOLLOW.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_MOSS_RIE = FeatureUtils.register("patch_moss_rie", Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.MOSS_CARPET.defaultBlockState(), 3).add(ModBlocks.WIDOWEED.get().defaultBlockState(), 1))), BlockPredicate.allOf(BlockPredicate.matchesBlock(Blocks.AIR, BlockPos.ZERO), BlockPredicate.allOf(BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.MOSSY_COBBLESTONE, ModBlocks.MASKONITE_BLOCK.get()), new BlockPos(0, -1, 0)))))));
    public static final Music RIE_WEALD_MUSIC = new Music(ModSounds.MUSIC_BIOME_RIE_WEALD, 1200, 12000, true);
    public static final Music RIE_WEALD_MUSIC_FOG = new Music(ModSounds.MUSIC_BIOME_RIE_WEALD_FOG, 1200, 12000, true);

}