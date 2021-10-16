package net.tropicraft.core.common.dimension.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.Random;

public class TropicsCaveCarver extends CaveWorldCarver {

    public TropicsCaveCarver(Codec<CaveCarverConfiguration> codec) {
        super(codec);//, 256);
        this.replaceableBlocks = ImmutableSet.<Block> builder().addAll(this.replaceableBlocks)
                .add(TropicraftBlocks.CORAL_SAND)
                .add(TropicraftBlocks.FOAMY_SAND)
                .add(TropicraftBlocks.MINERAL_SAND)
                .add(TropicraftBlocks.PACKED_PURIFIED_SAND)
                .add(TropicraftBlocks.PURIFIED_SAND)
                .add(TropicraftBlocks.VOLCANIC_SAND).build();
    }

    //TODO: SEE ABOUT IMPLEMNTING A SIMILAR MECHANISM WITH THE CARVER CONFIG LATER
    protected int getCaveY(Random rand) {
        if (rand.nextInt(5) == 0) {
            return rand.nextInt(240 + 8); // Add some evenly distributed caves in, in addition to the ones biased towards lower Y
        }
        return rand.nextInt(rand.nextInt(240) + 8);
    }

}
