package net.tropicraft.core.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.tropicraft.core.common.dimension.feature.TropicraftFeatures;
import net.tropicraft.core.common.dimension.feature.config.FruitTreeConfig;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class TropicraftTrees {
    public static final AbstractTreeGrower GRAPEFRUIT = create((random, beehive) ->
            TropicraftFeatures.FRUIT_TREE.configured(new FruitTreeConfig(TropicraftBlocks.GRAPEFRUIT_SAPLING, TropicraftBlocks.GRAPEFRUIT_LEAVES))
    );
    public static final AbstractTreeGrower LEMON = create((random, beehive) ->
            TropicraftFeatures.FRUIT_TREE.configured(new FruitTreeConfig(TropicraftBlocks.LEMON_SAPLING, TropicraftBlocks.LEMON_LEAVES))
    );
    public static final AbstractTreeGrower LIME = create((random, beehive) ->
            TropicraftFeatures.FRUIT_TREE.configured(new FruitTreeConfig(TropicraftBlocks.LIME_SAPLING, TropicraftBlocks.LIME_LEAVES))
    );
    public static final AbstractTreeGrower ORANGE = create((random, beehive) ->
            TropicraftFeatures.FRUIT_TREE.configured(new FruitTreeConfig(TropicraftBlocks.ORANGE_SAPLING, TropicraftBlocks.ORANGE_LEAVES))
    );

    public static final AbstractTreeGrower RAINFOREST = create((random, beehive) -> {
        final int treeType = random.nextInt(4);
        if (treeType == 0) {
            return TropicraftFeatures.TALL_TREE.configured(NoneFeatureConfiguration.INSTANCE);
        } else if (treeType == 1) {
            return TropicraftFeatures.SMALL_TUALUNG.configured(NoneFeatureConfiguration.INSTANCE);
        } else if (treeType == 2) {
            return TropicraftFeatures.UP_TREE.configured(NoneFeatureConfiguration.INSTANCE);
        } else {
            return TropicraftFeatures.LARGE_TUALUNG.configured(NoneFeatureConfiguration.INSTANCE);
        }
    });

    public static final AbstractTreeGrower PALM = create((random, beehive) -> {
        final int palmType = random.nextInt(3);
        if (palmType == 0) {
            return TropicraftFeatures.NORMAL_PALM_TREE.configured(NoneFeatureConfiguration.INSTANCE);
        } else if (palmType == 1) {
            return TropicraftFeatures.CURVED_PALM_TREE.configured(NoneFeatureConfiguration.INSTANCE);
        } else {
            return TropicraftFeatures.LARGE_PALM_TREE.configured(NoneFeatureConfiguration.INSTANCE);
        }
    });

    private static AbstractTreeGrower create(FeatureProvider featureProvider) {
        return new AbstractTreeGrower() {
            @Nullable
            @Override
            protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random random, boolean beehive) {
                return null;
            }

            @Override
            public boolean growTree(ServerLevel world, ChunkGenerator generator, BlockPos pos, BlockState sapling, Random random) {
                ConfiguredFeature<?, ?> feature = featureProvider.getFeature(random, this.hasFlowers(world, pos));
                if (feature == null) {
                    return false;
                }

                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
                if (feature.place(world, generator, random, pos)) {
                    return true;
                } else {
                    world.setBlock(pos, sapling, 4);
                    return false;
                }
            }

            private boolean hasFlowers(LevelAccessor world, BlockPos origin) {
                BlockPos min = origin.offset(-2, -1, -2);
                BlockPos max = origin.offset(2, 1, 2);
                for (BlockPos pos : BlockPos.MutableBlockPos.betweenClosed(min, max)) {
                    if (world.getBlockState(pos).is(BlockTags.FLOWERS)) {
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
