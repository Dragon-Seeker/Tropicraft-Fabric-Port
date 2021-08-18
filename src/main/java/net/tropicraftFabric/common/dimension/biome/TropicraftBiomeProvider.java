package net.tropicraftFabric.common.dimension.biome;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.dimension.layer.TropicraftLayerUtil;

import java.util.Objects;
import java.util.Set;

public class TropicraftBiomeProvider extends BiomeSource {
    public static final Codec<TropicraftBiomeProvider> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                Codec.LONG.fieldOf("seed").stable().forGetter(b -> b.seed),
                RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(b -> b.biomes)
        ).apply(instance, instance.stable(TropicraftBiomeProvider::new));
    });

    private static final Set<RegistryKey<Biome>> POSSIBLE_BIOMES = ImmutableSet.of(
            TropicraftBiomes.TROPICS,
            TropicraftBiomes.TROPICS_OCEAN,
            TropicraftBiomes.TROPICS_RIVER,
            TropicraftBiomes.TROPICS_BEACH,
            TropicraftBiomes.RAINFOREST_HILLS,
            TropicraftBiomes.RAINFOREST_PLAINS,
            TropicraftBiomes.RAINFOREST_MOUNTAINS,
            TropicraftBiomes.RAINFOREST_ISLAND_MOUNTAINS,
            TropicraftBiomes.KELP_FOREST
    );

    private final long seed;
    private final Registry<Biome> biomes;

    private final BiomeLayerSampler noiseLayer;

    public TropicraftBiomeProvider(long seed, Registry<Biome> biomes) {
        super(POSSIBLE_BIOMES.stream().map(biomes::get).filter(Objects::nonNull).map(biome -> () -> biome));

        this.seed = seed;
        this.biomes = biomes;

        this.noiseLayer = TropicraftLayerUtil.buildTropicsProcedure(seed, biomes);
    }

    public static void register() {
        Registry.register(Registry.BIOME_SOURCE, new Identifier(Constants.MODID, "tropics"), CODEC);
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public BiomeSource withSeed(long seed) {
        return new TropicraftBiomeProvider(seed, biomes);
    }

    @Override
    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        return noiseLayer.sample(biomes, x, z);
    }
}
