package net.tropicraft.core.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.tropicraft.core.common.dimension.feature.TropicraftFeatures;
import net.tropicraft.core.common.dimension.feature.config.FruitTreeConfig;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class TropicraftTrees {
    public static final SaplingGenerator GRAPEFRUIT = create((random, beehive) ->
            TropicraftFeatures.FRUIT_TREE.configure(new FruitTreeConfig(TropicraftBlocks.GRAPEFRUIT_SAPLING, TropicraftBlocks.GRAPEFRUIT_LEAVES))
    );
    public static final SaplingGenerator LEMON = create((random, beehive) ->
            TropicraftFeatures.FRUIT_TREE.configure(new FruitTreeConfig(TropicraftBlocks.LEMON_SAPLING, TropicraftBlocks.LEMON_LEAVES))
    );
    public static final SaplingGenerator LIME = create((random, beehive) ->
            TropicraftFeatures.FRUIT_TREE.configure(new FruitTreeConfig(TropicraftBlocks.LIME_SAPLING, TropicraftBlocks.LIME_LEAVES))
    );
    public static final SaplingGenerator ORANGE = create((random, beehive) ->
            TropicraftFeatures.FRUIT_TREE.configure(new FruitTreeConfig(TropicraftBlocks.ORANGE_SAPLING, TropicraftBlocks.ORANGE_LEAVES))
    );

    public static final SaplingGenerator RAINFOREST = create((random, beehive) -> {
        final int treeType = random.nextInt(4);
        if (treeType == 0) {
            return TropicraftFeatures.TALL_TREE.configure(DefaultFeatureConfig.INSTANCE);
        } else if (treeType == 1) {
            return TropicraftFeatures.SMALL_TUALUNG.configure(DefaultFeatureConfig.INSTANCE);
        } else if (treeType == 2) {
            return TropicraftFeatures.UP_TREE.configure(DefaultFeatureConfig.INSTANCE);
        } else {
            return TropicraftFeatures.LARGE_TUALUNG.configure(DefaultFeatureConfig.INSTANCE);
        }
    });

    public static final SaplingGenerator PALM = create((random, beehive) -> {
        final int palmType = random.nextInt(3);
        if (palmType == 0) {
            return TropicraftFeatures.NORMAL_PALM_TREE.configure(DefaultFeatureConfig.INSTANCE);
        } else if (palmType == 1) {
            return TropicraftFeatures.CURVED_PALM_TREE.configure(DefaultFeatureConfig.INSTANCE);
        } else {
            return TropicraftFeatures.LARGE_PALM_TREE.configure(DefaultFeatureConfig.INSTANCE);
        }
    });

    private static SaplingGenerator create(FeatureProvider featureProvider) {
        return new SaplingGenerator() {
            @Nullable
            @Override
            protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean beehive) {
                return null;
            }

            @Override
            public boolean generate(ServerWorld world, ChunkGenerator generator, BlockPos pos, BlockState sapling, Random random) {
                ConfiguredFeature<?, ?> feature = featureProvider.getFeature(random, this.hasFlowers(world, pos));
                if (feature == null) {
                    return false;
                }

                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
                if (feature.generate(world, generator, random, pos)) {
                    return true;
                } else {
                    world.setBlockState(pos, sapling, 4);
                    return false;
                }
            }

            private boolean hasFlowers(WorldAccess world, BlockPos origin) {
                BlockPos min = origin.add(-2, -1, -2);
                BlockPos max = origin.add(2, 1, 2);
                for (BlockPos pos : BlockPos.Mutable.iterate(min, max)) {
                    if (world.getBlockState(pos).isIn(BlockTags.FLOWERS)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    interface FeatureProvider {
        @Nullable
        ConfiguredFeature<?, ?> getFeature(Random random, boolean beehive);
    }
}
