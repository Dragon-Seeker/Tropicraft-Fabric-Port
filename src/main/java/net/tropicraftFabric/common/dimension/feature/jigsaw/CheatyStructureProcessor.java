package net.tropicraftFabric.common.dimension.feature.jigsaw;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.tropicraftFabric.mixins.StructureProcessorMixin;

public abstract class CheatyStructureProcessor extends StructureProcessor {
    protected boolean isAirOrWater(WorldView worldReaderIn, BlockPos pos) {
        return worldReaderIn.isAir(pos) || worldReaderIn.getBlockState(pos).getBlock() == Blocks.WATER;
    }

    protected boolean setBlockState(WorldView world, BlockPos pos, BlockState state) {
        if (world instanceof WorldAccess) {
            return ((WorldAccess) world).setBlockState(pos, state, 4 | 16);
        }
        return false;
    }
}
