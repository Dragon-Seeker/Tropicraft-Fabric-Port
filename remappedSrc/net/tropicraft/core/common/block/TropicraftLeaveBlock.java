package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.Random;

public class TropicraftLeaveBlock extends LeavesBlock {
    public static IntegerProperty EXTRADISTANCE;
    public static BooleanProperty PASTFIRSTCYCLE; //Property to check if there has even been a block update to prevent unwanted decay instantly

    public TropicraftLeaveBlock(FabricBlockSettings settings) {
        super(settings);

        this.registerDefaultState(this.defaultBlockState().setValue(DISTANCE, 7).setValue(EXTRADISTANCE, 20).setValue(PASTFIRSTCYCLE, false));
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) { //*
        //super.scheduledTick(state, world, pos, random);
        world.setBlock(pos, updateDistanceFromLeave(state, world, pos), 3);
    }

    //What This should be named is indirect block update but yarn mapping be bad
    @Override
    public void updateIndirectNeighbourShapes(BlockState state, LevelAccessor world, BlockPos pos, int flags, int maxUpdateDepth) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        DiagonalDirections[] directions = DiagonalDirections.values();
        int LENGTH = directions.length;

        for (int index = 0; index < LENGTH; ++index) {
            DiagonalDirections direction = directions[index];
            mutable.setWithOffset(pos, direction.getX(), direction.getY(), direction.getZ());
            BlockState blockState = world.getBlockState(mutable);
            if (blockState != Blocks.AIR.defaultBlockState() && blockState.getBlock() == this) {
                BlockState blockState2 = blockState.updateShape(Direction.UP, state, world, mutable, pos);
                Block.updateOrDestroy(blockState, blockState2, world, mutable, flags, maxUpdateDepth);
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) { //*
        return !(Boolean)state.getValue(PERSISTENT) && (Integer)state.getValue(EXTRADISTANCE) == 20;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) { //*
        if (state.getValue(EXTRADISTANCE) == 20 && state.getValue(PASTFIRSTCYCLE) == true) {
            super.randomTick(state, world, pos, random);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        int i = getDistanceFromLeave(neighborState) + 1;
        if (i != 1 || state.getValue(EXTRADISTANCE) >= i) {
            world.getBlockTicks().scheduleTick(pos, this, 1);
        }

        return state;
    }

    //TODO: MUST CHANGE DUE TO MINCRAFT CODE STUFFF
    private static BlockState updateDistanceFromLeave(BlockState state, LevelAccessor world, BlockPos pos) { //*
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        DiagonalDirections[] directionDiagonals = DiagonalDirections.values();

        int currentExtraDistance = 20; //*
        int length = directionDiagonals.length;

        for(int i = 0; i < length; ++i) {
            DiagonalDirections direction = directionDiagonals[i];
            mutable.setWithOffset(pos, direction.getX(), direction.getY(), direction.getZ());
            currentExtraDistance = Math.min(currentExtraDistance, getDistanceFromLeave(world.getBlockState(mutable)) + direction.additionAmount);
            if (currentExtraDistance == 1) {
                break;
            }
        }

        return state.setValue(EXTRADISTANCE, currentExtraDistance).setValue(PASTFIRSTCYCLE, true);
    }

    private static int getDistanceFromLeave(BlockState state) {
        if (BlockTags.LOGS.contains(state.getBlock())) {
            return 0;
        }

        else {
            return (state.getBlock() instanceof TropicraftLeaveBlock) ? state.getValue(EXTRADISTANCE) : 20;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { //*
        builder.add(new Property[]{DISTANCE, PERSISTENT, EXTRADISTANCE, PASTFIRSTCYCLE});
    }

    static {
        EXTRADISTANCE = IntegerProperty.create("extradistance", 1, 20);
        PASTFIRSTCYCLE = BooleanProperty.create("pastfirstcycle");
    }

}
