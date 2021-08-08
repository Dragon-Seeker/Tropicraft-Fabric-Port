package net.tropicraftFabric.common.dimension.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class UndergroundSeaPickleFeature extends Feature<DefaultFeatureConfig> {
    public UndergroundSeaPickleFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        BlockState surface = world.getBlockState(pos.down());
        if (!surface.isOf(Blocks.STONE) && !surface.isOf(Blocks.DIRT)) {
            return false;
        }

        if (world.getBlockState(pos).isOf(Blocks.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER)) {
            int count = random.nextInt(random.nextInt(4) + 1) + 1;
            if (surface.isOf(Blocks.DIRT)) {
                count = Math.min(count + random.nextInt(2), 4);
            }

            BlockState pickle = Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, count);
            world.setBlockState(pos, pickle, 2);
            return true;
        }

        return false;
    }
}
