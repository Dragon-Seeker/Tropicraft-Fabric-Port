package net.tropicraft.core.common.block;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.Locale;
import java.util.Random;

public class TropicTikiTorch extends Block {

    public enum TorchSection implements StringRepresentable {
        UPPER(2), MIDDLE(1), LOWER(0);

        final int height;

        TorchSection(int height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return this.getSerializedName();
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public static final EnumProperty<TorchSection> SECTION = EnumProperty.create("section", TorchSection.class);

    protected static final VoxelShape BASE_SHAPE = Shapes.box(0.4000000059604645D, 0.0D, 0.4000000059604645D, 0.6000000238418579D, 0.999999D, 0.6000000238418579D);
    protected static final VoxelShape TOP_SHAPE = Shapes.box(0.4000000059604645D, 0.0D, 0.4000000059604645D, 0.6000000238418579D, 0.6000000238418579D, 0.6000000238418579D);

    public TropicTikiTorch() {
        super(FabricBlockSettings.copyOf(Blocks.TORCH));
        this.registerDefaultState(defaultBlockState().setValue(SECTION, TorchSection.UPPER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SECTION);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, final BlockPos pos, CollisionContext context) {
        TorchSection section = state.getValue(SECTION);

        if (section == TorchSection.UPPER) {
            return TOP_SHAPE;
        } else {
            return BASE_SHAPE;
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) { //canPlaceAt
        if (canSupportCenter(world, pos.below(), Direction.UP)) { // can block underneath support torch
            return true;
        }
        else { // if not, is the block underneath a lower 2/3 tiki torch segment?
            BlockState blockstate = world.getBlockState(pos.below());
            return (blockstate.getBlock() == this && blockstate.getValue(SECTION) != TorchSection.UPPER) && super.canSurvive(state, world, pos); //canPlaceAt
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        if (placeShortTorchOn(context.getLevel().getBlockState(blockpos.below()))) {
            return defaultBlockState().setValue(SECTION, TorchSection.UPPER);
        }
        BlockState ret = defaultBlockState().setValue(SECTION, TorchSection.LOWER);
        return blockpos.getY() < context.getLevel().dimensionType().minY() - 1 &&
                context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) &&
                context.getLevel().getBlockState(blockpos.above(2)).canBeReplaced(context) ? ret : null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if(facing.getAxis() == Direction.Axis.Y){
            if(state.getValue(SECTION) == TorchSection.UPPER){
                return !state.canSurvive(world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, neighborState, world, currentPos, facingPos);
            }

            else{
                return !state.canSurvive(world, currentPos) || (neighborState == Blocks.AIR.defaultBlockState()) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, neighborState, world, currentPos, facingPos);
            }
        }

        else{
            return super.updateShape(state, facing, neighborState, world, currentPos, facingPos);
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
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        TorchSection section = state.getValue(SECTION);

        if (section == TorchSection.UPPER) return;

        worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(SECTION, TorchSection.MIDDLE), 3); //Constants.BlockFlags.DEFAULT : A Forge constant With a value of 3
        worldIn.setBlock(pos.above(2), this.defaultBlockState().setValue(SECTION, TorchSection.UPPER), 3);
    }

    private boolean placeShortTorchOn(BlockState state) {
        // Only place top block if it's on a fence/wall
        return state.is(BlockTags.FENCES) || state.is(BlockTags.WALLS);
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
        TorchSection section = state.getValue(SECTION);
        BlockPos base = pos.below(section.height);
        for (TorchSection otherSection : TorchSection.values()) {
            BlockPos pos2 = base.above(otherSection.height);
            BlockState state2 = world.getBlockState(pos2);
            if (state2.getBlock() == this && state2.getValue(SECTION) == otherSection) {
                super.playerDestroy(world, player, pos2, state2, blockEntity, stack);
                //world.setBlockState(pos2, Blocks.AIR.getDefaultState(), 35);
                world.setBlock(pos2, world.getFluidState(pos2).createLegacyBlock(), world.isClientSide ? 11 : 3);
            }
        }
        if(section == TorchSection.UPPER){
            super.playerDestroy(world, player, pos, state, blockEntity, stack);
            world.setBlock(pos, world.getFluidState(pos).createLegacyBlock(), world.isClientSide ? 11 : 3);
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
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) { //randomTick or scheduledTick
        boolean isTop = state.getValue(SECTION) == TorchSection.UPPER;
        if (isTop) {
            double d = pos.getX() + 0.5F;
            double d1 = pos.getY() + 0.7F;
            double d2 = pos.getZ() + 0.5F;

            world.addParticle(ParticleTypes.SMOKE, d, d1, d2, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, d, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
