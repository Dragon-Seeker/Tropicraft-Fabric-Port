package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;

public class TropicTrapdoorBlock extends TrapDoorBlock {
    public TropicTrapdoorBlock(Block block) {
        super(FabricBlockSettings.copyOf(block).noOcclusion());
    }
}
