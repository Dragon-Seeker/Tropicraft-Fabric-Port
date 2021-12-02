package net.tropicraft.core.common.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.world.level.block.LeavesBlock;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class DepreciatedTropicLeavesBlock extends LeavesBlock {
    public DepreciatedTropicLeavesBlock(FabricBlockSettings settings, boolean decay) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(PERSISTENT, !decay));
    }

}
