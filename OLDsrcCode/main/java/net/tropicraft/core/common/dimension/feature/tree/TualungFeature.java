package net.tropicraft.core.common.dimension.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

import static net.tropicraft.core.common.dimension.feature.TropicraftFeatureUtil.goesBeyondWorldSize;
import static net.tropicraft.core.common.dimension.feature.TropicraftFeatureUtil.isBBAvailable;

// TODO use TualungConfig, but requires extending Feature instead, which is a pain
public class TualungFeature extends RainforestTreeFeature {

    private int baseHeight;
    private int maxHeight;

    public TualungFeature(Codec<DefaultFeatureConfig> codec, int maxHeight, int baseHeight) {
        super(codec);
        this.baseHeight = baseHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random rand = context.getRandom();
        BlockPos pos = context.getOrigin();

        pos = pos.toImmutable();
        int i = pos.getX(); int j = pos.getY(); int k = pos.getZ();
        int height = rand.nextInt(maxHeight - baseHeight) + baseHeight + j;
        int branches = rand.nextInt(3) + 3;

        if (goesBeyondWorldSize(world, pos.getY(), height - j)) {
            return false;
        }

        if (height + 6 > 256) {
            return false;
        }

        if (!isBBAvailable(world, pos, height - j)) {
            return false;
        }

        if (!getSapling().canPlaceAt(getSapling().getDefaultState(), world, pos)) {
            return false;
        }

        setState(world, new BlockPos(i, j - 1, k), Blocks.DIRT.getDefaultState());
        setState(world, new BlockPos(i - 1, j - 1, k), Blocks.DIRT.getDefaultState());
        setState(world, new BlockPos(i + 1, j - 1, k), Blocks.DIRT.getDefaultState());
        setState(world, new BlockPos(i, j - 1, k - 1), Blocks.DIRT.getDefaultState());
        setState(world, new BlockPos(i, j - 1, k + 1), Blocks.DIRT.getDefaultState());

        for (int y = j; y < height; y++) {
            placeLog(world, i, y, k);
            placeLog(world, i - 1, y, k);
            placeLog(world, i + 1, y, k);
            placeLog(world, i, y, k - 1);
            placeLog(world, i, y, k + 1);
        }

        for (int x = 0; x < branches; x++) {
            int branchHeight = rand.nextInt(4) + 2 + height;
            int bx = rand.nextInt(15) - 8 + i;
            int bz = rand.nextInt(15) - 8 + k;

            placeBlockLine(world, new int[] { i + sign((bx - i) / 2), height, k + sign((bz - k) / 2) }, new int[] { bx, branchHeight, bz }, getLog());

            genCircle(world, bx, branchHeight, bz, 2, 1, getLeaf(), false);
            genCircle(world, bx, branchHeight + 1, bz, 3, 2, getLeaf(), false);
        }

        return true;
    }

    private int sign(int i) {
        return i == 0 ? 0 : i <= 0 ? -1 : 1;
    }
}
