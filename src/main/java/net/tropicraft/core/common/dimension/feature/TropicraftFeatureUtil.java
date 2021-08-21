package net.tropicraft.core.common.dimension.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.TreeFeature;

public class TropicraftFeatureUtil {

    public static boolean goesBeyondWorldSize(final StructureWorldAccess world, final int y, final int height) {
        return y < 1 || y + height + 1 > world.getDimensionHeight();
    }

    public static boolean isBBAvailable(final StructureWorldAccess world, final BlockPos pos, final int height) {
        for (int y = 0; y <= 1 + height; y++) {
            BlockPos checkPos = pos.up(y);
            int size = 1;
            if (checkPos.getY() < 0 || checkPos.getY() >= world.getDimensionHeight()) {
                return false;
            }

            if (y == 0) {
                size = 0;
            }

            if (y >= (1 + height) - 2) {
                size = 2;
            }

            if (BlockPos.stream(checkPos.add(-size, 0, -size), checkPos.add(size, 0, size)).anyMatch(p -> !TreeFeature.isAirOrLeaves(world, p))) {
                return false;
            }
        }

        return true;
    }
    
    protected static boolean isSoil(final WorldAccess world, final BlockPos pos) {
        final BlockState blockState = world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL;
    }
}
