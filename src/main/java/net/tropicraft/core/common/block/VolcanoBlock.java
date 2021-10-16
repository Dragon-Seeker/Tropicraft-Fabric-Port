package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.tropicraft.core.common.block.blockentity.VolcanoBlockEntity;
import net.tropicraft.core.common.registry.TropicBlockEntities;
import net.tropicraft.core.common.registry.TropicraftEntities;

public class VolcanoBlock extends BaseEntityBlock {

    public VolcanoBlock(Properties settings) {
        super(settings);
    }

    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VolcanoBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, TropicBlockEntities.VOLCANO, (world1, pos, state1, be) -> VolcanoBlockEntity.tick(world1, pos, state1, be));
    }


}
