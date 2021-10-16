package net.tropicraft.core.common.dimension.surfacebuilders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class TropicsSurfaceBuilder extends SurfaceBuilder<TropicsSurfaceBuilder.Config> {
    public TropicsSurfaceBuilder(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int i, long seed, Config config) {
        TernarySurfaceConfig selectedConfig = config.land;
        if (noise > 1.5) {
            if (chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR_WG, x, z) + 1 >= seaLevel) {
                selectedConfig = config.sandy;
            } else {
                selectedConfig = config.sandyUnderwater;
            }
        }

        SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, i, seed, selectedConfig);
    }

    public static final class Config implements SurfaceConfig {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> {
            return instance.group(
                    TernarySurfaceConfig.CODEC.fieldOf("land").forGetter(c -> c.land),
                    TernarySurfaceConfig.CODEC.fieldOf("sandy").forGetter(c -> c.sandy),
                    TernarySurfaceConfig.CODEC.fieldOf("sandy_underwater").forGetter(c -> c.sandyUnderwater)
            ).apply(instance, Config::new);
        });

        public final TernarySurfaceConfig land;
        public final TernarySurfaceConfig sandy;
        public final TernarySurfaceConfig sandyUnderwater;

        public Config(TernarySurfaceConfig land, TernarySurfaceConfig sandy, TernarySurfaceConfig sandyUnderwater) {
            this.land = land;
            this.sandy = sandy;
            this.sandyUnderwater = sandyUnderwater;
        }

        @Override
        public BlockState getTopMaterial() {
            return this.land.getTopMaterial();
        }

        @Override
        public BlockState getUnderMaterial() {
            return this.land.getUnderMaterial();
        }

        @Override
        public BlockState getUnderwaterMaterial() {
            return this.sandy.getUnderwaterMaterial();
        }
    }
}
