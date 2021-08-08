package net.tropicraftFabric.common.world.feature;

import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.registry.TropicraftBlocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class tropicConfiguredFeatures {
    public static ConfiguredFeature<TreeFeatureConfig, ?> BASIC_TEST_TREE, PALM_TREE;

    public static void registerFeatures() {
        BASIC_TEST_TREE = register("basic_test_tree", Feature.TREE.configure(Configs.BASIC_TEST_TREE_CONFIG));
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Constants.MODID, id), configuredFeature);
    }



    public static class Configs {
        public static final TreeFeatureConfig BASIC_TEST_TREE_CONFIG = (
                new TreeFeatureConfig.Builder(
                        new SimpleBlockStateProvider(
                                TropicraftBlocks.MAHOGANY_LOG.getDefaultState()),
                        new SimpleBlockStateProvider(TropicraftBlocks.MAHOGANY_LEAVES.getDefaultState()),
                            new BlobFoliagePlacer(UniformIntDistribution.of(2),
                                        UniformIntDistribution.of(0), 3),
                            new StraightTrunkPlacer(4, 2, 0),
                        new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build();

    }
}
