package net.tropicraftFabric.common.world.feature.tree;

import net.tropicraftFabric.common.world.feature.tropicConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class tropicBasicSaplingGenerator extends SaplingGenerator {

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return tropicConfiguredFeatures.BASIC_TEST_TREE;
    }
}
