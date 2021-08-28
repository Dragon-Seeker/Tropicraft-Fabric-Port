package net.tropicraft.core.mixinExtensions;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.tropicraft.core.common.block.TestDirection;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import net.tropicraft.core.mixins.AbstractBlockstateAccessor;

public interface BlockStateExtension {
    default void diagonalupdateNeighbors(WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        TestDirection[] directions = TestDirection.values();
        int LENGTH = directions.length;


        for (int index = 0; index < LENGTH; ++index) {
            TestDirection direction = directions[index];
            mutable.set(pos, direction.getX(), direction.getY(), direction.getZ());
            BlockState blockState = world.getBlockState(mutable);
            if (blockState != Blocks.AIR.getDefaultState() && blockState.getBlock() == TropicraftBlocks.PALM_LEAVES) {
                BlockState blockState2 = blockState.getStateForNeighborUpdate(Direction.UP, ((AbstractBlockstateAccessor)this).invokeAsBlockState(), world, mutable, pos);
                Block.replace(blockState, blockState2, world, mutable, flags, maxUpdateDepth);
            }
        }
    }

    default boolean currentBlockCheckPalm() {
        Block currentBlock = ((AbstractBlock.AbstractBlockState)this).getBlock();
        if(currentBlock == TropicraftBlocks.PALM_LEAVES) {
            return true; //The main block is a Palm Block and proceed to update diagonally to blocks that were palm trees leaves
        }

        else{
            return false; //There was no palm blocks, not block updating diagonally
        }
    }
}
