package net.tropicraftFabric.common.block;

import net.tropicraftFabric.common.registry.TropicraftBlocks;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Map;

public class TropicBambooPot extends Block {
    private static final Map<Block, Block> FLOWER_TO_POTTED = Maps.newHashMap();
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
    private final Block flora;

    public TropicBambooPot(Block content, Settings settings) {
        super(settings);
        this.flora = content;
        FLOWER_TO_POTTED.put(content, this);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack possibleFloralItem = player.getStackInHand(hand);
        Item item = possibleFloralItem.getItem();

        Block block;
        if(item instanceof BlockItem){
            block = FLOWER_TO_POTTED.getOrDefault(((BlockItem)item).getBlock(), Blocks.AIR);
        }
        else {
            block = Blocks.AIR;
        }

        boolean isAirBlock = block == Blocks.AIR;
        boolean isFlowerBlockAir = this.flora == Blocks.AIR;
        if (isAirBlock != isFlowerBlockAir) {
            if (isFlowerBlockAir) {
                world.setBlockState(pos, block.getDefaultState(), 3);
                player.incrementStat(Stats.POT_FLOWER);

                if (!player.abilities.creativeMode) {
                    possibleFloralItem.decrement(1);
                }
            }
            else {
                ItemStack currentFloraInPot = new ItemStack(this.flora);
                if (possibleFloralItem.isEmpty()) {
                    player.setStackInHand(hand, currentFloraInPot);
                }

                else if (!player.giveItemStack(currentFloraInPot)) {
                    player.dropItem(currentFloraInPot, false);
                }

                world.setBlockState(pos, TropicraftBlocks.BAMBOO_FLOWER_POT.getDefaultState(), 3);
            }

            return ActionResult.success(world.isClient);

        }
        else {

            return ActionResult.CONSUME;
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        if(this.flora == Blocks.AIR ){
            return super.getPickStack(world, pos, state);
        }

        return new ItemStack(this.flora);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(direction == Direction.DOWN && !state.canPlaceAt(world, pos)){
            return Blocks.AIR.getDefaultState();
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public Block getContent() {
        return this.flora;
    }



}
