package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import net.tropicraft.core.common.block.blockentity.VolcanoBlockEntity;

public class VolcanoBlock extends BlockWithEntity {

    public VolcanoBlock(FabricBlockSettings settings) {
        super(settings);
    }

    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new VolcanoBlockEntity();
    }


}
