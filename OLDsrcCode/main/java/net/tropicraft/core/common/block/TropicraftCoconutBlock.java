package net.tropicraft.core.common.block;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class TropicraftCoconutBlock extends FacingBlock {
    private static final VoxelShape COCONUT_AABB = Block.createCuboidShape(4, 0.0D, 4, 12, 10, 12);

    public TropicraftCoconutBlock() {
        super(FabricBlockSettings.of(Material.GOURD).hardness(2.0f).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.STONE));

    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COCONUT_AABB;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, final BlockPos pos, ShapeContext context) {
        return COCONUT_AABB;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        Direction dir = state.get(FACING);
        BlockPos checkPos = pos.offset(dir);
        return worldIn.getBlockState(checkPos).getBlock() == Blocks.GRINDSTONE // coconut yeeters allowed
                || Block.sideCoversSmallSquare(worldIn, checkPos, dir.getOpposite());
    }

    @Override
    @Deprecated
    public BlockState getStateForNeighborUpdate(BlockState stateIn, Direction facing, BlockState facingState, WorldAccess worldIn, BlockPos currentPos, BlockPos facingPos) {
        return stateIn.get(FACING) == facing && !stateIn.canPlaceAt(worldIn, currentPos)
                ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, net.minecraft.util.BlockMirror mirrorIn) {
        return state.with(FACING, mirrorIn.apply(state.get(FACING)));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getSide().getOpposite());
    }

}
