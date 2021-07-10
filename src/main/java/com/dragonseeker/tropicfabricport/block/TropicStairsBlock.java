package com.dragonseeker.tropicfabricport.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.BlockState;

public class TropicStairsBlock extends StairsBlock {

    public TropicStairsBlock(Block block){
        super(block.getDefaultState(), FabricBlockSettings.copyOf(block));
    }

}
