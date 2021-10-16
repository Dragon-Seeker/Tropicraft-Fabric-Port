package net.tropicraft.core.common.block;

import net.tropicraft.core.common.block.blockentity.TropicBambooChestBlockEntity;
import net.tropicraft.core.common.registry.TropicBlockEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TropicBambooChestBlock extends ChestBlock {
    private final Block parent;

    public TropicBambooChestBlock(Block source){
        super(FabricBlockSettings.copyOf(source).noOcclusion(), () -> TropicBlockEntities.BAMBOO_CHEST);
        this.parent = source;
    }


    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TropicBambooChestBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        //With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Deprecated
    public float getDestroyProgress(BlockState state, Player player, BlockGetter world, BlockPos pos) {
        final TropicBambooChestBlockEntity blockEntity = (TropicBambooChestBlockEntity) world.getBlockEntity(pos);
        if (blockEntity != null && blockEntity.isUnbreakable()) {
            return 0.0f;
        }
        return super.getDestroyProgress(state, player, world, pos);
    }
}
