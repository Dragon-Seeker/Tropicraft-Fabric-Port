package net.tropicraftFabric.common.dimension.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.CaveCarver;
import net.tropicraftFabric.common.registry.TropicraftBlocks;

import java.util.Random;

public class TropicsCaveCarver extends CaveCarver {

    public TropicsCaveCarver(Codec<ProbabilityConfig> codec) {
        super(codec, 256);
        this.alwaysCarvableBlocks = ImmutableSet.<Block> builder().addAll(this.alwaysCarvableBlocks)
                .add(TropicraftBlocks.CORAL_SAND)
                .add(TropicraftBlocks.FOAMY_SAND)
                .add(TropicraftBlocks.MINERAL_SAND)
                .add(TropicraftBlocks.PACKED_PURIFIED_SAND)
                .add(TropicraftBlocks.PURIFIED_SAND)
                .add(TropicraftBlocks.VOLCANIC_SAND).build();
    }

    @Override
    protected int getCaveY(Random rand) {
        if (rand.nextInt(5) == 0) {
            return rand.nextInt(240 + 8); // Add some evenly distributed caves in, in addition to the ones biased towards lower Y
        }
        return rand.nextInt(rand.nextInt(240) + 8);
    }
}
