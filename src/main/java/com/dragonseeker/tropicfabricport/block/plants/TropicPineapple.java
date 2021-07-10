package com.dragonseeker.tropicfabricport.block.plants;

import com.dragonseeker.tropicfabricport.registry.TropicBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class TropicPineapple extends TallFlowerBlock {

    public static final int TOTAL_GROW_TICKS = 7;

    //public static final IntProperty STAGE = Properties.AGE_7;
    public static final IntProperty STAGE = IntProperty.of("age", 0, 7);

    public TropicPineapple() {
        super(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
        //this.setDefaultState(super.getDefaultState().with(STAGE, 0));
        //this.setDefaultState(super.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }


    @Override
    protected void appendProperties(final StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF, STAGE);
    }

    public void placeAt(WorldAccess world, BlockPos pos, int flags) {
        world.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER), flags);
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), flags);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return state.getBlock() == TropicBlocks.PINEAPPLE &&
               state.get(TropicPineapple.HALF) == DoubleBlockHalf.LOWER &&
               world.getBlockState(pos.up()).isAir();
    }
    /*
    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }
    */

    @Override
    public void grow(final ServerWorld world, final Random random, final BlockPos pos, final BlockState state) {
        final int currentStage = state.get(STAGE);
        if (currentStage < TOTAL_GROW_TICKS) {
            final BlockState growthState = state.with(STAGE, currentStage + 1);
            world.setBlockState(pos, growthState, 4);
        } else {
            final BlockState above = world.getBlockState(pos.up());

            // Don't bother placing if it's already there
            if (above.getBlock() == this) return;
            if (state.get(HALF) == DoubleBlockHalf.UPPER) return;

            // Place actual pineapple plant above stem
            final BlockState fullGrowth = state.with(HALF, DoubleBlockHalf.UPPER);
            world.setBlockState(pos.up(), fullGrowth, 3);
        }
    }

    @Override
    public void randomTick(final BlockState state, final ServerWorld world, final BlockPos pos, final Random random) {
        if (pos.getY() > world.getHeight() - 2) {
            return;
        }

        // Current metadata
        int growth = state.get(STAGE);

        if (state.getBlock() == this && growth <= TOTAL_GROW_TICKS && world.isAir(pos.up()) && state.get(HALF) == DoubleBlockHalf.LOWER) {
            if (growth >= TOTAL_GROW_TICKS - 1) {
                // Set current state
                BlockState growthState = state.with(STAGE, TOTAL_GROW_TICKS);
                world.setBlockState(pos, growthState, 3 | 4);

                // Place actual pineapple plant above stem
                BlockState fullGrowth = growthState.with(HALF, DoubleBlockHalf.UPPER);
                world.setBlockState(pos.up(), fullGrowth, 3);
            } else {
                BlockState growthState = state.with(STAGE, growth + 1);
                world.setBlockState(pos, growthState, 3 | 4);
            }
        }
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            super.onBreak(worldIn, pos, state, player);
        } else {
            worldIn.syncWorldEvent(player, 2001, pos, getRawIdFromState(state)); //worldIn.playEvent(player, 2001, pos, getStateId(state));
            dropStacks(state, worldIn, pos, null, player, player.getMainHandStack());
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(canPlaceAt(state, world, pos)){
            return state;
        }
        return Blocks.AIR.getDefaultState();
    }

    /*
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, World worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (canPlaceAt(stateIn, worldIn, currentPos)) {
            return stateIn;
        }
        return Blocks.AIR.getDefaultState();
    }
     */

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return worldIn.getBlockState(pos.down()).getBlock() == TropicBlocks.PINEAPPLE;
        } else {
            return canPlantOnTop(state, worldIn, pos);
        }
    }

    @Override
    public void onPlaced(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, LivingEntity p_180633_4_, ItemStack p_180633_5_) {
        // override super behavior of placing top half of double flower by default
    }

    /*
    private boolean canPlaceBlockAt(WorldView worldIn, BlockPos pos) {
        final BlockState belowState = worldIn.getBlockState(pos.down());
        return belowState.getBlock().canSustainPlant(belowState, worldIn, pos.down(), Direction.UP, this);
    }
     */

    @Override
    protected boolean canPlantOnTop(BlockState state, BlockView worldIn, BlockPos pos) {
        return state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock().isIn(TagRegistry.block(new Identifier("c", "dirt")))  || state.getBlock() == Blocks.FARMLAND || state.getBlock() == this;
    }

}


