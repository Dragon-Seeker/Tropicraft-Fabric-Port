package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

public class TropicStairsBlock extends StairsBlock {

    public TropicStairsBlock(Block block){
        super(block.getDefaultState(), FabricBlockSettings.copyOf(block));
    }

}
