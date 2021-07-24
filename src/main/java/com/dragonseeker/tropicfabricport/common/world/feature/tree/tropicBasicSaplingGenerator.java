package com.dragonseeker.tropicfabricport.common.world.feature.tree;

import com.dragonseeker.tropicfabricport.common.world.feature.tropicConfiguredFeatures;
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
