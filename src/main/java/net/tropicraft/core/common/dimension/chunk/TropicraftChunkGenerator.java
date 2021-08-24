package net.tropicraft.core.common.dimension.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.tropicraft.Constants;
import net.tropicraft.core.mixins.NoiseChuckGeneratorAccessor;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class TropicraftChunkGenerator extends NoiseChunkGenerator {
    public static final Codec<TropicraftChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                BiomeSource.CODEC.fieldOf("biome_source").forGetter(g -> g.populationSource),
                Codec.LONG.fieldOf("seed").stable().forGetter(g -> g.seed),
                ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(g -> g.settings)
        ).apply(instance, instance.stable(TropicraftChunkGenerator::new));
    });

    private final VolcanoGenerator volcano;
    private final long seed;

    public TropicraftChunkGenerator(BiomeSource biomeProvider, long seed, Supplier<ChunkGeneratorSettings> settings) {
        super(biomeProvider, seed, settings);
        this.seed = seed;
        this.volcano = new VolcanoGenerator(seed, biomeProvider);

        // maintain parity with old noise. cursed? very. i'm sorry :(
        ChunkRandom random = new ChunkRandom(seed);
        new OctavePerlinNoiseSampler(random, IntStream.rangeClosed(-15, 0));
        new OctavePerlinNoiseSampler(random, IntStream.rangeClosed(-15, 0));
        new OctavePerlinNoiseSampler(random, IntStream.rangeClosed(-7, 0));
        new OctaveSimplexNoiseSampler(random, IntStream.rangeClosed(-3, 0));

        random.next(2620);
        ((NoiseChuckGeneratorAccessor)this).setDensityNoise(new OctavePerlinNoiseSampler(random, IntStream.rangeClosed(-15, 0)));

        //this.densityNoise =
    }

    public static void register() {
        Registry.register(Registry.CHUNK_GENERATOR, new Identifier(Constants.MODID, "tropics"), CODEC);
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ChunkGenerator withSeed(long seed) {
        return new TropicraftChunkGenerator(this.populationSource.withSeed(seed), seed, this.settings);
    }

    @Override
    public int getSpawnHeight() {
        return getSeaLevel() + 1;
    }

    @Override
    public void populateNoise(WorldAccess world, StructureAccessor structures, Chunk chunk) {
        super.populateNoise(world, structures, chunk);

        ChunkPos chunkPos = chunk.getPos();
        volcano.generate(chunkPos.x, chunkPos.z, chunk, random);
    }

    @Override
    public int getHeight(int x, int z, Type heightmapType) {
        int height = super.getHeight(x, z, heightmapType);
        if (heightmapType != Type.OCEAN_FLOOR && heightmapType != Type.OCEAN_FLOOR_WG) {
            return Math.max(height, this.volcano.getVolcanoHeight(height, x, z));
        }
        return height;
    }
}
