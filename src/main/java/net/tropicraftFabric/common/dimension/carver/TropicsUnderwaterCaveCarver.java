package net.tropicraftFabric.common.dimension.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.UnderwaterCaveCarver;
import net.tropicraftFabric.common.registry.TropicraftBlocks;

import java.util.Random;

public class TropicsUnderwaterCaveCarver extends UnderwaterCaveCarver {

    public TropicsUnderwaterCaveCarver(Codec<ProbabilityConfig> codec) {
        super(codec);
        this.alwaysCarvableBlocks = ImmutableSet.<Block> builder().addAll(this.alwaysCarvableBlocks)
                .add(TropicraftBlocks.CORAL_SAND)
                .add(TropicraftBlocks.FOAMY_SAND)
                .add(TropicraftBlocks.MINERAL_SAND)
                .add(TropicraftBlocks.PACKED_PURIFIED_SAND)
                .add(TropicraftBlocks.PURIFIED_SAND)
                .add(TropicraftBlocks.VOLCANIC_SAND).build();
    }

    @Override
    protected boolean isRegionUncarvable(Chunk chunkIn, int chunkX, int chunkZ, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        return false;
    }
    
    @Override
    protected float getTunnelSystemWidth(Random rand) {
        float f = rand.nextFloat() * 3.0F + rand.nextFloat();
        if (rand.nextInt(10) == 0) {
           f *= rand.nextFloat() * rand.nextFloat() * 5.0F + 1.0F;
        }

        return f;
    }
    
    @Override
    protected int getCaveY(Random random) {
        return random.nextInt(random.nextInt(240) + 8);
    }
}
