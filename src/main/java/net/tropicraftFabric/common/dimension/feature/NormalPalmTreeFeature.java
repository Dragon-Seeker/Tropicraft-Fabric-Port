package net.tropicraftFabric.common.dimension.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;

import java.util.Random;

import static net.tropicraftFabric.common.dimension.feature.TropicraftFeatureUtil.goesBeyondWorldSize;
import static net.tropicraftFabric.common.dimension.feature.TropicraftFeatureUtil.isBBAvailable;

public class NormalPalmTreeFeature extends PalmTreeFeature {
    public NormalPalmTreeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        pos = pos.toImmutable();

        int height = random.nextInt(4) + 6;

        if (goesBeyondWorldSize(world, pos.getY(), height)) {
            return false;
        }

        if (!isBBAvailable(world, pos, height)) {
            return false;
        }

        if (!getSapling().canPlaceAt(getSapling().getDefaultState(), world, pos)) {
            return false;
        }

        if (world.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK) {
            world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), 3);
        }

        int i = pos.getX(), j = pos.getY(), k = pos.getZ();

        placeLeaf(world, i, j + height + 2, k);
        placeLeaf(world, i, j + height + 1, k + 1);
        placeLeaf(world, i, j + height + 1, k + 2);
        placeLeaf(world, i, j + height + 1, k + 3);
        placeLeaf(world, i, j + height, k + 4);
        placeLeaf(world, i + 1, j + height + 1, k);
        placeLeaf(world, i + 2, j + height + 1, k);
        placeLeaf(world, i + 3, j + height + 1, k);
        placeLeaf(world, i + 4, j + height, k);
        placeLeaf(world, i, j + height + 1, k - 1);
        placeLeaf(world, i, j + height + 1, k - 2);
        placeLeaf(world, i, j + height + 1, k - 3);
        placeLeaf(world, i, j + height, k - 4);
        placeLeaf(world, i - 1, j + height + 1, k);
        placeLeaf(world, i - 1, j + height + 1, k - 1);
        placeLeaf(world, i - 1, j + height + 1, k + 1);
        placeLeaf(world, i + 1, j + height + 1, k - 1);
        placeLeaf(world, i + 1, j + height + 1, k + 1);
        placeLeaf(world, i - 2, j + height + 1, k);
        placeLeaf(world, i - 3, j + height + 1, k);
        placeLeaf(world, i - 4, j + height, k);
        placeLeaf(world, i + 2, j + height + 1, k + 2);
        placeLeaf(world, i + 2, j + height + 1, k - 2);
        placeLeaf(world, i - 2, j + height + 1, k + 2);
        placeLeaf(world, i - 2, j + height + 1, k - 2);
        placeLeaf(world, i + 3, j + height, k + 3);
        placeLeaf(world, i + 3, j + height, k - 3);
        placeLeaf(world, i - 3, j + height, k + 3);
        placeLeaf(world, i - 3, j + height, k - 3);

        for (int j1 = 0; j1 < height + 2; j1++) {
            BlockPos logPos = pos.up(j1);
            if (TreeFeature.canReplace(world, logPos)) {
                placeLog(world, logPos);
            }
        }

        spawnCoconuts(world, new BlockPos(i, j + height, k), random, 2, getLeaf());

        return true;
    }
}
