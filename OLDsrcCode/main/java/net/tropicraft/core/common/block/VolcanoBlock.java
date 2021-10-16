package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.tropicraft.core.common.block.blockentity.VolcanoBlockEntity;
import net.tropicraft.core.common.registry.TropicBlockEntities;
import net.tropicraft.core.common.registry.TropicraftEntities;

public class VolcanoBlock extends BlockWithEntity {

    public VolcanoBlock(FabricBlockSettings settings) {
        super(settings);
    }

    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VolcanoBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, TropicBlockEntities.VOLCANO, (world1, pos, state1, be) -> VolcanoBlockEntity.tick(world1, pos, state1, be));
    }


}
