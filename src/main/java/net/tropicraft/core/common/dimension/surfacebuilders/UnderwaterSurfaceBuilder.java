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

public class UnderwaterSurfaceBuilder extends SurfaceBuilder<UnderwaterSurfaceBuilder.Config> {
    public UnderwaterSurfaceBuilder(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int i, long seed, Config config) {
        TernarySurfaceConfig selectedConfig = config.beach;
        if (startHeight > seaLevel + 5) {
            selectedConfig = config.land;
        }
        if (chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR_WG, x, z) + 1 < seaLevel) {
            selectedConfig = config.underwater;
        }

        SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, i, seed, selectedConfig);
    }

    public static final class Config implements SurfaceConfig {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> {
            return instance.group(
                    TernarySurfaceConfig.CODEC.fieldOf("beach").forGetter(c -> c.beach),
                    TernarySurfaceConfig.CODEC.fieldOf("land").forGetter(c -> c.land),
                    TernarySurfaceConfig.CODEC.fieldOf("underwater").forGetter(c -> c.underwater)
            ).apply(instance, Config::new);
        });

        public final TernarySurfaceConfig beach;
        public final TernarySurfaceConfig land;
        public final TernarySurfaceConfig underwater;

        public Config(TernarySurfaceConfig beach, TernarySurfaceConfig land, TernarySurfaceConfig underwater) {
            this.beach = beach;
            this.land = land;
            this.underwater = underwater;
        }

        @Override
        public BlockState getTopMaterial() {
            return this.beach.getTopMaterial();
        }

        @Override
        public BlockState getUnderMaterial() {
            return this.beach.getUnderMaterial();
        }

        @Override
        public BlockState getUnderwaterMaterial() {
            return this.beach.getUnderwaterMaterial();
        }
    }
}
