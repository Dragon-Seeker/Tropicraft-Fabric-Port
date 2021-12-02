package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;

public class TropicStairsBlock extends StairBlock {

    public TropicStairsBlock(Block block){
        super(block.defaultBlockState(), FabricBlockSettings.copyOf(block));
    }

}
