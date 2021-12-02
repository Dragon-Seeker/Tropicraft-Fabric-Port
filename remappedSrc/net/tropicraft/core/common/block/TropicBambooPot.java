package net.tropicraft.core.common.block;

import net.tropicraft.core.common.registry.TropicraftBlocks;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.Map;

public class TropicBambooPot extends Block {
    private static final Map<Block, Block> FLOWER_TO_POTTED = Maps.newHashMap();
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
    private final Block flora;

    public TropicBambooPot(Block content, Properties settings) {
        super(settings);
        this.flora = content;
        FLOWER_TO_POTTED.put(content, this);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack possibleFloralItem = player.getItemInHand(hand);
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
                world.setBlock(pos, block.defaultBlockState(), 3);
                player.awardStat(Stats.POT_FLOWER);

                if (!player.abilities.instabuild) {
                    possibleFloralItem.shrink(1);
                }
            }
            else {
                ItemStack currentFloraInPot = new ItemStack(this.flora);
                if (possibleFloralItem.isEmpty()) {
                    player.setItemInHand(hand, currentFloraInPot);
                }

                else if (!player.addItem(currentFloraInPot)) {
                    player.drop(currentFloraInPot, false);
                }

                world.setBlock(pos, TropicraftBlocks.BAMBOO_FLOWER_POT.defaultBlockState(), 3);
            }

            return InteractionResult.sidedSuccess(world.isClientSide);

        }
        else {

            return InteractionResult.CONSUME;
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        if(this.flora == Blocks.AIR ){
            return super.getCloneItemStack(world, pos, state);
        }

        return new ItemStack(this.flora);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if(direction == Direction.DOWN && !state.canSurvive(world, pos)){
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        return false;
    }

    public Block getContent() {
        return this.flora;
    }



}
