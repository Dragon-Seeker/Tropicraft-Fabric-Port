package net.tropicraftFabric.common.block;

import net.tropicraftFabric.common.registry.TropicraftItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class CoffeeBushBlock extends CropBlock {

    public static final IntProperty AGE = IntProperty.of("age", 0, 6);

    /** Number of bushes high this plant can grow */
    public static final int MAX_HEIGHT = 3;

    /** The growth rate when this plant is fertile */
    public static final int GROWTH_RATE_FERTILE = 10;

    /** The growth rate when this plant is infertile */
    public static final int GROWTH_RATE_INFERTILE = 20;

    public CoffeeBushBlock(FabricBlockSettings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected ItemConvertible getSeedsItem() {
        return TropicraftItems.RAW_COFFEE_BEAN;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        // Try to grow up
        if (worldIn.isAir(pos.up())) {
            int height;
            BlockPos ground = pos;
            for (height = 1; worldIn.getBlockState(ground = ground.down()).getBlock() == this; ++height);

            final BlockState blockState = worldIn.getBlockState(ground);
            if (height < MAX_HEIGHT && worldIn.random.nextInt(blockState.getBlock().canPlaceAt(blockState, worldIn, ground) ? GROWTH_RATE_FERTILE : GROWTH_RATE_INFERTILE) == 0) { //blockState.getBlock().isFertile(blockState, worldIn, ground)
                worldIn.setBlockState(pos.up(), getDefaultState());
            }
        }

        super.randomTick(state, worldIn, pos, rand);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        if (state.get(AGE) == getMaxAge()) {
            world.setBlockState(pos, state.with(AGE, 0));
            final int count = 1 + player.getRandom().nextInt(3);
            ItemStack stack = new ItemStack(TropicraftItems.RAW_COFFEE_BEAN, count);
            dropStack(world, pos, stack);
            return world.isClient() ? ActionResult.SUCCESS : ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        //builder.add(AGE);
        builder.add(new Property[]{AGE});
    }


    //TODO: Check if the tag check for canPlantOnTop is working wth other blocks dirt
    @Override
    protected boolean canPlantOnTop(BlockState state, BlockView worldIn, BlockPos pos) {
        return state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock().isIn(TagRegistry.block(new Identifier("c", "dirt")))  || state.getBlock() == Blocks.FARMLAND || state.getBlock() == this;
    }
}
