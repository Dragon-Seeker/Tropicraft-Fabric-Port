package com.dragonseeker.tropicfabricport.block;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;

public class TropicLadderBlock extends LadderBlock {
    public TropicLadderBlock() {
        super(FabricBlockSettings.copyOf(Blocks.BAMBOO));
    }
}
