package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class TropicSand extends FallingBlock{
    public static final BooleanProperty UNDERWATER = BooleanProperty.create("underwater");

    public TropicSand(FabricBlockSettings settings) {
        super(settings);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(UNDERWATER);
    }

    /*
    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return Blocks.SAND.canSustainPlant(state, world, pos, facing, plantable);
    }

    @Override
    public void onEntityWalk(final World world, final BlockPos pos, final Entity entity) {
        final BlockState state = world.getBlockState(pos);

        // If not black sands
        if (state.getBlock() != TropicraftBlocks.VOLCANIC_SAND.get()) {
            return;
        }

        if (entity instanceof LivingEntity) {
            final LivingEntity living = (LivingEntity)entity;
            final ItemStack stack = living.getItemStackFromSlot(EquipmentSlotType.FEET);

            // If entity isn't wearing anything on their feetsies
            if (stack.isEmpty()) {
                living.attackEntityFrom(DamageSource.LAVA, 0.5F);
            }
        } else {
            entity.attackEntityFrom(DamageSource.LAVA, 0.5F);
        }
    }

    @Override
    public BlockState getStateForPlacement(final BlockItemUseContext context) {
        final IFluidState upState = context.getWorld().getFluidState(context.getPos().up());
        boolean waterAbove = false;
        if (!upState.isEmpty()) {
            waterAbove = true;
        }
        return this.getDefaultState().with(UNDERWATER, waterAbove);
    }

    @Override
    @Deprecated
    public void neighborChanged(final BlockState state, final World world, final BlockPos pos, final Block block, final BlockPos pos2, boolean isMoving) {
        final IFluidState upState = world.getFluidState(pos.up());
        boolean underwater = upState.getFluid().isEquivalentTo(Fluids.WATER);
        if (underwater != state.get(UNDERWATER)) {
            world.setBlockState(pos, state.with(UNDERWATER, underwater), Constants.BlockFlags.BLOCK_UPDATE);
        }
        super.neighborChanged(state, world, pos, block, pos2, isMoving);
    }

     */
}
