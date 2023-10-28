package net.dainplay.rpgworldmod.world.feature;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.features.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class RPGFeatures {

    public static final Feature<BlockStateConfiguration> SPIKY_IVY = register("spiky_ivy_feature", new SpikyIvyFeature(BlockStateConfiguration.CODEC));
    public static final Feature<BlockStateConfiguration> TREE_HOLLOW_WEST = register("tree_hollow_feature_west", new TreeHollowFeatureWest(BlockStateConfiguration.CODEC));
    public static final Feature<BlockStateConfiguration> TREE_HOLLOW_EAST = register("tree_hollow_feature_east", new TreeHollowFeatureEast(BlockStateConfiguration.CODEC));
    public static final Feature<BlockStateConfiguration> TREE_HOLLOW_NORTH = register("tree_hollow_feature_north", new TreeHollowFeatureNorth(BlockStateConfiguration.CODEC));
    public static final Feature<BlockStateConfiguration> TREE_HOLLOW_SOUTH = register("tree_hollow_feature_south", new TreeHollowFeatureSouth(BlockStateConfiguration.CODEC));

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String key, F value)
    {
        RPGworldMod.FEATURE_REGISTER.register(key, () -> value);
        return value;
    }

    public static void setup() {}
}
