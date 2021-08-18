package net.tropicraftFabric.common.dimension.layer;

import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public final class TropicraftRiverLayer implements CrossSamplingLayer {
    private final TropicraftBiomeIds biomeIds;

    public TropicraftRiverLayer(TropicraftBiomeIds biomeIds) {
        this.biomeIds = biomeIds;
    }

    @Override
    public int sample(LayerRandomnessSource random, int north, int east, int south, int west, int center) {
        if (center != north || center != east || center != south || center != west) {
            return biomeIds.river;
        }

        return -1;
    }
}
