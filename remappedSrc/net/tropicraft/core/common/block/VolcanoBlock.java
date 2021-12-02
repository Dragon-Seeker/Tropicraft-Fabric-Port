package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.tropicraft.core.common.block.blockentity.VolcanoBlockEntity;

public class VolcanoBlock extends BaseEntityBlock {

    public VolcanoBlock(FabricBlockSettings settings) {
        super(settings);
    }

    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockEntity newBlockEntity(BlockGetter world) {
        return new VolcanoBlockEntity();
    }


}
