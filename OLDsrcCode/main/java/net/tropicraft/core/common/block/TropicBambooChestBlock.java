package net.tropicraft.core.common.block;

import net.tropicraft.core.common.block.blockentity.TropicBambooChestBlockEntity;
import net.tropicraft.core.common.registry.TropicBlockEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TropicBambooChestBlock extends ChestBlock {
    private final Block parent;

    public TropicBambooChestBlock(Block source){
        super(FabricBlockSettings.copyOf(source).nonOpaque(), () -> TropicBlockEntities.BAMBOO_CHEST);
        this.parent = source;
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TropicBambooChestBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        //With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Deprecated
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        final TropicBambooChestBlockEntity blockEntity = (TropicBambooChestBlockEntity) world.getBlockEntity(pos);
        if (blockEntity != null && blockEntity.isUnbreakable()) {
            return 0.0f;
        }
        return super.calcBlockBreakingDelta(state, player, world, pos);
    }
}
