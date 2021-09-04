package net.tropicraft.core.common.dimension.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

import static net.tropicraft.core.common.dimension.feature.TropicraftFeatureUtil.goesBeyondWorldSize;
import static net.tropicraft.core.common.dimension.feature.TropicraftFeatureUtil.isBBAvailable;

public class TallRainforestTreeFeature extends RainforestTreeFeature {

    private static final int VINE_CHANCE = 5;
    private static final int SMALL_LEAF_CHANCE = 3;
    private static final int SECOND_CANOPY_CHANCE = 3;

    public TallRainforestTreeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    private boolean isSoil(WorldAccess world, BlockPos pos) {
        return getSapling().canPlaceAt(getSapling().getDefaultState(), world, pos);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random rand = context.getRandom();
        BlockPos pos = context.getOrigin();

        pos = pos.toImmutable();
        int i = pos.getX(); int j = pos.getY(); int k = pos.getZ();
        final int height = rand.nextInt(15) + 15;

        if (goesBeyondWorldSize(world, pos.getY(), height)) {
            return false;
        }

        if (!isBBAvailable(world, pos, height)) {
            return false;
        }

        if (!isSoil(world, pos.down())) {
            return false;
        }

        if (!isSoil(world, pos.east().down())) {
            return false;
        }

        if (!isSoil(world, pos.west().down())) {
            return false;
        }

        if (!isSoil(world, pos.north().down())) {
            return false;
        }

        if (!isSoil(world, pos.south().down())) {
            return false;
        }

        setState(world, new BlockPos(i, j - 1, k), Blocks.DIRT.getDefaultState());
        setState(world, new BlockPos(i - 1, j - 1, k), Blocks.DIRT.getDefaultState());
        setState(world, new BlockPos(i + 1, j - 1, k), Blocks.DIRT.getDefaultState());
        setState(world, new BlockPos(i, j - 1, k - 1), Blocks.DIRT.getDefaultState());
        setState(world, new BlockPos(i, j - 1, k + 1), Blocks.DIRT.getDefaultState());

        for (int y = j; y < j + height; y++) {
            placeLog(world, i, y, k);
            placeLog(world, i - 1, y, k);
            placeLog(world, i + 1, y, k);
            placeLog(world, i, y, k - 1);
            placeLog(world, i, y, k + 1);

            if (y - j > height / 2 && rand.nextInt(SMALL_LEAF_CHANCE) == 0) {
                int nx = rand.nextInt(3) - 1 + i;
                int nz = rand.nextInt(3) - 1 + k;

                genCircle(world, new BlockPos(nx, y + 1, nz), 1, 0, getLeaf(), false);
                genCircle(world, nx, y, nz, 2, 1, getLeaf(), false);
            }
            if (y - j > height - (height / 4) && y - j < height - 3 && rand.nextInt(SECOND_CANOPY_CHANCE) == 0) {
                int nx = i + rand.nextInt(9) - 4;
                int nz = k + rand.nextInt(9) - 4;

                int leafSize = rand.nextInt(3) + 5;

                genCircle(world, nx, y + 3, nz, leafSize - 2, 0, getLeaf(), false);
                genCircle(world, nx, y + 2, nz, leafSize - 1, leafSize - 3, getLeaf(), false);
                genCircle(world, nx, y + 1, nz, leafSize, leafSize - 1, getLeaf(), false);

                placeBlockLine(world, new int[] { i, y - 2, k }, new int[] { nx, y + 2, nz }, getLog());
            }
        }

        int leafSize = rand.nextInt(5) + 9;

        genCircle(world, i, j + height, k, leafSize - 2, 0, getLeaf(), false);
        genCircle(world, i, j + height - 1, k, leafSize - 1, leafSize - 4, getLeaf(), false);
        genCircle(world, i, j + height - 2, k, leafSize, leafSize - 1, getLeaf(), false);

        return true;
    }
}
