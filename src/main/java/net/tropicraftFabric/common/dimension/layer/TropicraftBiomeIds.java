package net.tropicraftFabric.common.dimension.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.tropicraftFabric.common.dimension.biome.TropicraftBiomes;

public final class TropicraftBiomeIds {
    public final int ocean;
    public final int kelpForest;
    public final int land;
    public final int river;
    public final int beach;
    public final int islandMountains;
    public final int rainforestPlains;
    public final int rainforestHills;
    public final int rainforestMountains;

    public TropicraftBiomeIds(Registry<Biome> biomes) {
        this.ocean = getId(biomes, TropicraftBiomes.TROPICS_OCEAN);
        this.kelpForest = getId(biomes, TropicraftBiomes.KELP_FOREST);
        this.land = getId(biomes, TropicraftBiomes.TROPICS);
        this.river = getId(biomes, TropicraftBiomes.TROPICS_RIVER);
        this.beach = getId(biomes, TropicraftBiomes.TROPICS_BEACH);
        this.islandMountains = getId(biomes, TropicraftBiomes.RAINFOREST_ISLAND_MOUNTAINS);
        this.rainforestPlains = getId(biomes, TropicraftBiomes.RAINFOREST_PLAINS);
        this.rainforestHills = getId(biomes, TropicraftBiomes.RAINFOREST_HILLS);
        this.rainforestMountains = getId(biomes, TropicraftBiomes.RAINFOREST_MOUNTAINS);
    }

    private static int getId(Registry<Biome> biomes, RegistryKey<Biome> key) {
        return biomes.getRawId(biomes.get(key));
    }

    public boolean isOcean(final int biome) {
        return biome == this.ocean || biome == this.kelpForest;
    }

    public boolean isRiver(final int biome) {
        return biome == this.river;
    }

    public boolean isLand(final int biome) {
        return biome == this.land;
    }
}
