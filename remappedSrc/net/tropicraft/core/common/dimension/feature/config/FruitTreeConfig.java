package net.tropicraft.core.common.dimension.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.tropicraft.core.common.registry.TropicraftBlocks;

public final class FruitTreeConfig implements FeatureConfiguration {
    public static final Codec<FruitTreeConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                BlockState.CODEC.fieldOf("wood").forGetter(c -> c.wood),
                BlockState.CODEC.fieldOf("sapling").forGetter(c -> c.sapling),
                BlockState.CODEC.fieldOf("leaves").forGetter(c -> c.leaves),
                BlockState.CODEC.fieldOf("fruit_leaves").forGetter(c -> c.fruitLeaves)
        ).apply(instance, FruitTreeConfig::new);
    });

    public final BlockState wood;
    public final BlockState sapling;
    public final BlockState leaves;
    public final BlockState fruitLeaves;

    public FruitTreeConfig(BlockState wood, BlockState sapling, BlockState leaves, BlockState fruitLeaves) {
        this.wood = wood;
        this.sapling = sapling;
        this.leaves = leaves;
        this.fruitLeaves = fruitLeaves;
    }

    public FruitTreeConfig(BlockState sapling, BlockState fruitLeaves) {
        this(Blocks.OAK_LOG.defaultBlockState(), sapling, TropicraftBlocks.FRUIT_LEAVES.defaultBlockState(), fruitLeaves);
    }

    public FruitTreeConfig(Block sapling, Block fruitLeaves) {
        this(sapling.defaultBlockState(), fruitLeaves.defaultBlockState());
    }
}
