package net.tropicraftFabric.common.dimension.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.CachingLayerSampler;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.source.BiomeLayerSampler;

public class TropicraftLookupLayer extends BiomeLayerSampler {
    private final CachingLayerSampler area;

    public TropicraftLookupLayer(LayerFactory<CachingLayerSampler> areaFactory) {
        super(() -> null);
        this.area = areaFactory.make();
    }

    // the default layer delegated to Forge's registry which isn't populated from dynamic registries
    // we look up our produced biome ids from the same registry that we use here, so this is safe
    @Override
    public Biome sample(Registry<Biome> biomes, int x, int z) {
        int id = this.area.sample(x, z);

        Biome biome = biomes.get(id);
        if (biome == null) {
            throw new IllegalStateException("Unknown biome id emitted by layers: " + id);
        }

        return biome;
    }
}
