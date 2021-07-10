package com.dragonseeker.tropicfabricport.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class TropicLeavesBlock extends LeavesBlock {
    public TropicLeavesBlock() {
        super(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
                .breakByTool(FabricToolTags.HOES)
                .strength(0.2f)
                .nonOpaque()
                .sounds(BlockSoundGroup.GRASS)
                .breakByTool(FabricToolTags.SHEARS)
                .breakByHand(true));

    }


    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        // ignore decay
    }




}
