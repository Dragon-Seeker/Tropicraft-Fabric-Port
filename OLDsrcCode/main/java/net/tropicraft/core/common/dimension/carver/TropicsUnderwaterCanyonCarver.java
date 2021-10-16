package net.tropicraft.core.common.dimension.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.RavineCarverConfig;
import net.minecraft.world.gen.carver.UnderwaterCanyonCarver;
import net.minecraft.world.gen.carver.UnderwaterCaveCarver;
//import net.minecraft.world.gen.carver.UnderwaterRavineCarver;
import net.tropicraft.core.common.registry.TropicraftBlocks;

public class TropicsUnderwaterCanyonCarver extends UnderwaterCanyonCarver {

    public TropicsUnderwaterCanyonCarver(Codec<RavineCarverConfig> codec) {
        super(codec);
        this.alwaysCarvableBlocks = ImmutableSet.<Block>builder().addAll(this.alwaysCarvableBlocks)
                .add(TropicraftBlocks.CORAL_SAND)
                .add(TropicraftBlocks.FOAMY_SAND)
                .add(TropicraftBlocks.MINERAL_SAND)
                .add(TropicraftBlocks.PACKED_PURIFIED_SAND)
                .add(TropicraftBlocks.PURIFIED_SAND)
                .add(TropicraftBlocks.VOLCANIC_SAND)
                .build();
    }
}
