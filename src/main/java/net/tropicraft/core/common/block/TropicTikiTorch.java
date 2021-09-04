package net.tropicraft.core.common.block;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Locale;
import java.util.Random;

public class TropicTikiTorch extends Block {

    public enum TorchSection implements StringIdentifiable {
        UPPER(2), MIDDLE(1), LOWER(0);

        final int height;

        TorchSection(int height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return this.asString();
        }

        @Override
        public String asString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public static final EnumProperty<TorchSection> SECTION = EnumProperty.of("section", TorchSection.class);

    protected static final VoxelShape BASE_SHAPE = VoxelShapes.cuboid(0.4000000059604645D, 0.0D, 0.4000000059604645D, 0.6000000238418579D, 0.999999D, 0.6000000238418579D);
    protected static final VoxelShape TOP_SHAPE = VoxelShapes.cuboid(0.4000000059604645D, 0.0D, 0.4000000059604645D, 0.6000000238418579D, 0.6000000238418579D, 0.6000000238418579D);

    public TropicTikiTorch() {
        super(FabricBlockSettings.copyOf(Blocks.TORCH));
        this.setDefaultState(getDefaultState().with(SECTION, TorchSection.UPPER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SECTION);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, final BlockPos pos, ShapeContext context) {
        TorchSection section = state.get(SECTION);

        if (section == TorchSection.UPPER) {
            return TOP_SHAPE;
        } else {
            return BASE_SHAPE;
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) { //canPlaceAt
        if (sideCoversSmallSquare(world, pos.down(), Direction.UP)) { // can block underneath support torch
            return true;
        }
        else { // if not, is the block underneath a lower 2/3 tiki torch segment?
            BlockState blockstate = world.getBlockState(pos.down());
            return (blockstate.getBlock() == this && blockstate.get(SECTION) != TorchSection.UPPER) && super.canPlaceAt(state, world, pos); //canPlaceAt
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockPos blockpos = context.getBlockPos();
        if (placeShortTorchOn(context.getWorld().getBlockState(blockpos.down()))) {
            return getDefaultState().with(SECTION, TorchSection.UPPER);
        }
        BlockState ret = getDefaultState().with(SECTION, TorchSection.LOWER);
        return blockpos.getY() < context.getWorld().getDimension().getMinimumY() - 1 &&
                context.getWorld().getBlockState(blockpos.up()).canReplace(context) &&
                context.getWorld().getBlockState(blockpos.up(2)).canReplace(context) ? ret : null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis() == Direction.Axis.Y){
            if(state.get(SECTION) == TorchSection.UPPER){
                return !state.canPlaceAt(world, currentPos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, neighborState, world, currentPos, facingPos);
            }

            else{
                return !state.canPlaceAt(world, currentPos) || (neighborState == Blocks.AIR.getDefaultState()) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, neighborState, world, currentPos, facingPos);
            }
        }

        else{
            return super.getStateForNeighborUpdate(state, facing, neighborState, world, currentPos, facingPos);
        }


        /*
        if(facing.getAxis() == Direction.Axis.Y){
            if(state.get(SECTION) == TorchSection.UPPER){
                return !state.canPlaceAt(world, currentPos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, neighborState, world, currentPos, facingPos);
            }

            else{
                return !state.canPlaceAt(world, currentPos) || (neighborState == Blocks.AIR.getDefaultState()) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, neighborState, world, currentPos, facingPos);
            }
        }

        else{
            return super.getStateForNeighborUpdate(state, facing, neighborState, world, currentPos, facingPos);
        }
         */
    }

    @Override
    public void onPlaced(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        TorchSection section = state.get(SECTION);

        if (section == TorchSection.UPPER) return;

        worldIn.setBlockState(pos.up(), this.getDefaultState().with(SECTION, TorchSection.MIDDLE), 3); //Constants.BlockFlags.DEFAULT : A Forge constant With a value of 3
        worldIn.setBlockState(pos.up(2), this.getDefaultState().with(SECTION, TorchSection.UPPER), 3);
    }

    private boolean placeShortTorchOn(BlockState state) {
        // Only place top block if it's on a fence/wall
        return state.isIn(BlockTags.FENCES) || state.isIn(BlockTags.WALLS);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
        TorchSection section = state.get(SECTION);
        BlockPos base = pos.down(section.height);
        for (TorchSection otherSection : TorchSection.values()) {
            BlockPos pos2 = base.up(otherSection.height);
            BlockState state2 = world.getBlockState(pos2);
            if (state2.getBlock() == this && state2.get(SECTION) == otherSection) {
                super.afterBreak(world, player, pos2, state2, blockEntity, stack);
                //world.setBlockState(pos2, Blocks.AIR.getDefaultState(), 35);
                world.setBlockState(pos2, world.getFluidState(pos2).getBlockState(), world.isClient ? 11 : 3);
            }
        }
        if(section == TorchSection.UPPER){
            super.afterBreak(world, player, pos, state, blockEntity, stack);
            world.setBlockState(pos, world.getFluidState(pos).getBlockState(), world.isClient ? 11 : 3);
        }
    }

    /*
    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        boolean ret = false;
        TorchSection section = state.get(SECTION);
        BlockPos base = pos.down(section.height);
        for (TorchSection otherSection : TorchSection.values()) {
            BlockPos pos2 = base.up(otherSection.height);
            BlockState state2 = world.getBlockState(pos2);
            if (state2.getBlock() == this && state2.get(SECTION) == otherSection) {
                if (player.isCreative()) {
                    ret |= super.removedByPlayer(state2, world, pos2, player, willHarvest, fluid);
                } else {
                    this.onBreak(world, pos2, state2, player);
                    ret = true;
                }
            }//World world, BlockPos
        }
        return ret;
    }
     */

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rand) { //randomTick or scheduledTick
        boolean isTop = state.get(SECTION) == TorchSection.UPPER;
        if (isTop) {
            double d = pos.getX() + 0.5F;
            double d1 = pos.getY() + 0.7F;
            double d2 = pos.getZ() + 0.5F;

            world.addParticle(ParticleTypes.SMOKE, d, d1, d2, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, d, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
