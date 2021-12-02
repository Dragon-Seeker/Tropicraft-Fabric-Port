package net.tropicraft.core.common.block.plants;

import net.tropicraft.core.common.registry.TropicraftBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import java.util.Random;

public class TropicPineapple extends TallFlowerBlock {

    public static final int TOTAL_GROW_TICKS = 7;

    //public static final IntProperty STAGE = Properties.AGE_7;
    public static final IntegerProperty STAGE = IntegerProperty.create("age", 0, 7);

    public TropicPineapple() {
        super(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS));
        //this.setDefaultState(super.getDefaultState().with(STAGE, 0));
        //this.setDefaultState(super.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if(canSurvive(state, world, pos)){
            return state;
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public void setPlacedBy(Level p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, LivingEntity p_180633_4_, ItemStack p_180633_5_) {
        // override super behavior of placing top half of double flower by default
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return world.getBlockState(pos.below()).getBlock() == TropicraftBlocks.PINEAPPLE;
        } else {
            BlockPos blockPos = pos.below();
            return mayPlaceOn(world.getBlockState(blockPos), world, pos);
        }
    }

    /*
    public void placeAt(WorldAccess world, BlockPos pos, int flags) {
        world.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER), flags);
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), flags);
    }
     */

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        //worldIn.getBlockState(pos.down());
        return state.is(Blocks.GRASS_BLOCK) || state.getBlock().is(TagRegistry.block(new ResourceLocation("c", "dirt"))) || state.is(Blocks.FARMLAND) || state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT) || state.is(Blocks.PODZOL) || state.is(TropicraftBlocks.MINERAL_SAND);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            super.playerWillDestroy(worldIn, pos, state, player);
        } else {
            worldIn.levelEvent(player, 2001, pos, getId(state)); //worldIn.playEvent(player, 2001, pos, getStateId(state));
            dropResources(state, worldIn, pos, null, player, player.getMainHandItem());
        }
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return state.getBlock() == TropicraftBlocks.PINEAPPLE &&
               state.getValue(TropicPineapple.HALF) == DoubleBlockHalf.LOWER &&
               world.getBlockState(pos.above()).isAir();
    }
    /*
    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }
    */

    @Override
    public void performBonemeal(final ServerLevel world, final Random random, final BlockPos pos, final BlockState state) {
        final int currentStage = state.getValue(STAGE);
        if (currentStage < TOTAL_GROW_TICKS) {
            final BlockState growthState = state.setValue(STAGE, currentStage + 1);
            world.setBlock(pos, growthState, 4);
        } else {
            final BlockState above = world.getBlockState(pos.above());

            // Don't bother placing if it's already there
            if (above.getBlock() == this) return;
            if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return;

            // Place actual pineapple plant above stem
            final BlockState fullGrowth = state.setValue(HALF, DoubleBlockHalf.UPPER);
            world.setBlock(pos.above(), fullGrowth, 3);
        }
    }

    @Override
    public void randomTick(final BlockState state, final ServerLevel world, final BlockPos pos, final Random random) {
        if (pos.getY() > world.getMaxBuildHeight() - 2) {
            return;
        }

        // Current metadata
        int growth = state.getValue(STAGE);

        if (state.getBlock() == this && growth <= TOTAL_GROW_TICKS && world.isEmptyBlock(pos.above()) && state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            if (growth >= TOTAL_GROW_TICKS - 1) {
                // Set current state
                BlockState growthState = state.setValue(STAGE, TOTAL_GROW_TICKS);
                world.setBlock(pos, growthState, 3 | 4);

                // Place actual pineapple plant above stem
                BlockState fullGrowth = growthState.setValue(HALF, DoubleBlockHalf.UPPER);
                world.setBlock(pos.above(), fullGrowth, 3);
            } else {
                BlockState growthState = state.setValue(STAGE, growth + 1);
                world.setBlock(pos, growthState, 3 | 4);
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
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, STAGE);
    }

}


