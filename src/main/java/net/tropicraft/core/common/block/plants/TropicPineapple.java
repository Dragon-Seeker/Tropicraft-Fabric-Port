package net.tropicraft.core.common.block.plants;

import net.tropicraft.core.common.registry.TropicraftBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
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
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(canPlaceAt(state, world, pos)){
            return state;
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public void onPlaced(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, LivingEntity p_180633_4_, ItemStack p_180633_5_) {
        // override super behavior of placing top half of double flower by default
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return world.getBlockState(pos.down()).getBlock() == TropicraftBlocks.PINEAPPLE;
        } else {
            BlockPos blockPos = pos.down();
            return canPlantOnTop(world.getBlockState(blockPos), world, pos);
        }
    }

    /*
    public void placeAt(WorldAccess world, BlockPos pos, int flags) {
        world.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER), flags);
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), flags);
    }
     */

    @Override
    protected boolean canPlantOnTop(BlockState state, BlockView worldIn, BlockPos pos) {
        //worldIn.getBlockState(pos.down());
        return state.isOf(Blocks.GRASS_BLOCK) || state.isIn(TagRegistry.block(new Identifier("c", "dirt"))) || state.isOf(Blocks.FARMLAND) || state.isOf(Blocks.DIRT) || state.isOf(Blocks.COARSE_DIRT) || state.isOf(Blocks.PODZOL) || state.isOf(TropicraftBlocks.MINERAL_SAND);
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
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return state.getBlock() == TropicraftBlocks.PINEAPPLE &&
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

    /*
    private boolean canPlaceBlockAt(WorldView worldIn, BlockPos pos) {
        final BlockState belowState = worldIn.getBlockState(pos.down());
        return belowState.getBlock().canSustainPlant(belowState, worldIn, pos.down(), Direction.UP, this);
    }
     */

    @Override
    protected void appendProperties(final StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF, STAGE);
    }

}


