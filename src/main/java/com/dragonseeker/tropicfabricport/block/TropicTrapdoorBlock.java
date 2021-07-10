package com.dragonseeker.tropicfabricport.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.TrapdoorBlock;

public class TropicTrapdoorBlock extends TrapdoorBlock {
    public TropicTrapdoorBlock(Block block) {
        super(FabricBlockSettings.copyOf(block).nonOpaque());
    }
}
