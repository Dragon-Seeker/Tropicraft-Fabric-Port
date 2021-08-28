package net.tropicraft.core.common.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.State;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class TropicTestLeavesBlock extends LeavesBlock {
    public static final IntProperty EXTRADISTANCE;
    public final Boolean DECAY;


    public TropicTestLeavesBlock(FabricBlockSettings settings, boolean decay) {
        super(settings);

        this.setDefaultState(this.getDefaultState().with(DISTANCE, 7).with(PERSISTENT, !decay).with(EXTRADISTANCE, 12));
        this.DECAY = decay;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //super.scheduledTick(state, world, pos, random);



        //else{
            //int tempThisDistance = (Integer)state.get(DISTANCE);
            //if(tempThisDistance == 7) {
                world.setBlockState(pos, updateDistanceFromLeave(state, world, pos), 3);
            //}
        //}
    }


    @Override
    public boolean hasRandomTicks(BlockState state) {
        return (Integer)state.get(DISTANCE) == 7 && !(Boolean)state.get(PERSISTENT) && (Integer)state.get(EXTRADISTANCE) == 12;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //BlockState updatedState = updateDistanceFromLeave(state, world, pos);
        //world.setBlockState(pos, updatedState, 3);

        if (!(Boolean)state.get(PERSISTENT) && (Integer)state.get(DISTANCE) == 7 && (Integer)state.get(EXTRADISTANCE) == 12) {
            dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }

    }


    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int i = getDistanceFromLeave(neighborState) + 1;
        if (i != 1 || (Integer)state.get(EXTRADISTANCE) >= i) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }

        return state;
        //return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    //TODO: MUST CHANGE DUE TO MINCRAFT CODE STUFFF
    private static BlockState updateDistanceFromLeave(BlockState state, WorldAccess world, BlockPos pos) {
        int i = 12;
        int i2 = 7;

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        TestDirection[] directionSet = TestDirection.values();
        int length = directionSet.length;
        for(int index = 0; index < length; ++index) {
            TestDirection direction = directionSet[index];
            mutable.set(pos, direction.getX(), direction.getY(), direction.getZ());
            i = Math.min(i, getDistanceFromLeave(world.getBlockState(mutable)) + direction.additionAmount);
            if (i == 1) {
                break;
            }
        }

        Direction[] directions = Direction.values();
        int length2 = directions.length;
        for(int index = 0; index < length2; ++index) {
            Direction direction = directions[index];
            mutable.set(pos, direction);
            i2 = Math.min(i2, getDistanceFromLog(world.getBlockState(mutable)) + 1);
            if (i2 == 1) {
                break;
            }
        }

        return (BlockState)state.with(EXTRADISTANCE, i).with(DISTANCE, i2);
    }



    private static int getDistanceFromLog(BlockState state) {
        if (BlockTags.LOGS.contains(state.getBlock())) {
            return 0;
        } else {
            return state.getBlock() instanceof LeavesBlock ? (Integer)state.get(DISTANCE) : 7;
        }
    }

    private static int getDistanceFromLeave(BlockState state) {
        if (BlockTags.LOGS.contains(state.getBlock())) {
            return 0;
        }

        else {
            return (state.getBlock() instanceof TropicTestLeavesBlock) ? (Integer) state.get(EXTRADISTANCE) : 12;
        }
    }






    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{DISTANCE, PERSISTENT, EXTRADISTANCE});
    }

    static {
        EXTRADISTANCE = IntProperty.of("extradistance", 1, 12);
    }
}
