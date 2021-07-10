package com.dragonseeker.tropicfabricport.block;

import com.dragonseeker.tropicfabricport.block.blockentity.TropicBambooChestBlockEntity;
import com.dragonseeker.tropicfabricport.registry.TropicBlockEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TropicBambooChestBlock extends ChestBlock {
    private final Block parent;

    public TropicBambooChestBlock(Block source){
        super(FabricBlockSettings.copyOf(source).nonOpaque(), () -> TropicBlockEntities.BAMBOO_CHEST);
        this.parent = source;
    }


    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TropicBambooChestBlockEntity();
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
