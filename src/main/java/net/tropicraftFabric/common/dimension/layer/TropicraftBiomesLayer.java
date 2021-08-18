package net.tropicraftFabric.common.dimension.layer;

import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public final class TropicraftBiomesLayer implements IdentitySamplingLayer {
    private final TropicraftBiomeIds biomeIds;
    private final int[] landIds;

    public TropicraftBiomesLayer(TropicraftBiomeIds biomeIds) {
        this.biomeIds = biomeIds;
        this.landIds = new int[] { biomeIds.land, biomeIds.rainforestPlains };
    }

    @Override
    public int sample(LayerRandomnessSource iNoiseRandom, int center) {
        if (biomeIds.isLand(center)) {
            return landIds[iNoiseRandom.nextInt(landIds.length)];
        }

        return center;
    }
}
