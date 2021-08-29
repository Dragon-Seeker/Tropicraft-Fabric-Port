package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.Random;

public class TropicraftLeaveBlock extends LeavesBlock {
    public static IntProperty EXTRADISTANCE;
    public static BooleanProperty PASTFIRSTCYCLE; //Property to check if there has even been a block update to prevent unwanted decay instantly

    public TropicraftLeaveBlock(FabricBlockSettings settings) {
        super(settings);

        this.setDefaultState(this.getDefaultState().with(DISTANCE, 7).with(EXTRADISTANCE, 20).with(PASTFIRSTCYCLE, false));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) { //*
        //super.scheduledTick(state, world, pos, random);
        world.setBlockState(pos, updateDistanceFromLeave(state, world, pos), 3);
    }

    //What This should be named is indirect block update but yarn mapping be bad
    @Override
    public void prepare(BlockState state, WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        DiagonalDirections[] directions = DiagonalDirections.values();
        int LENGTH = directions.length;

        for (int index = 0; index < LENGTH; ++index) {
            DiagonalDirections direction = directions[index];
            mutable.set(pos, direction.getX(), direction.getY(), direction.getZ());
            BlockState blockState = world.getBlockState(mutable);
            if (blockState != Blocks.AIR.getDefaultState() && blockState.getBlock() == this) {
                BlockState blockState2 = blockState.getStateForNeighborUpdate(Direction.UP, state, world, mutable, pos);
                Block.replace(blockState, blockState2, world, mutable, flags, maxUpdateDepth);
            }
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) { //*
        return !(Boolean)state.get(PERSISTENT) && (Integer)state.get(EXTRADISTANCE) == 20;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) { //*
        if (state.get(EXTRADISTANCE) == 20 && state.get(PASTFIRSTCYCLE) == true) {
            super.randomTick(state, world, pos, random);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int i = getDistanceFromLeave(neighborState) + 1;
        if (i != 1 || state.get(EXTRADISTANCE) >= i) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }

        return state;
    }

    //TODO: MUST CHANGE DUE TO MINCRAFT CODE STUFFF
    private static BlockState updateDistanceFromLeave(BlockState state, WorldAccess world, BlockPos pos) { //*
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        DiagonalDirections[] directionDiagonals = DiagonalDirections.values();

        int currentExtraDistance = 20; //*
        int length = directionDiagonals.length;

        for(int i = 0; i < length; ++i) {
            DiagonalDirections direction = directionDiagonals[i];
            mutable.set(pos, direction.getX(), direction.getY(), direction.getZ());
            currentExtraDistance = Math.min(currentExtraDistance, getDistanceFromLeave(world.getBlockState(mutable)) + direction.additionAmount);
            if (currentExtraDistance == 1) {
                break;
            }
        }

        return state.with(EXTRADISTANCE, currentExtraDistance).with(PASTFIRSTCYCLE, true);
    }

    private static int getDistanceFromLeave(BlockState state) {
        if (BlockTags.LOGS.contains(state.getBlock())) {
            return 0;
        }

        else {
            return (state.getBlock() instanceof TropicraftLeaveBlock) ? state.get(EXTRADISTANCE) : 20;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { //*
        builder.add(new Property[]{DISTANCE, PERSISTENT, EXTRADISTANCE, PASTFIRSTCYCLE});
    }

    static {
        EXTRADISTANCE = IntProperty.of("extradistance", 1, 20);
        PASTFIRSTCYCLE = BooleanProperty.of("pastfirstcycle");
    }

}
