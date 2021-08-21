package net.tropicraft.core.common.dimension.layer;

import net.minecraft.world.biome.layer.type.DiagonalCrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public final class TropicraftBeachLayer implements DiagonalCrossSamplingLayer {
    private final TropicraftBiomeIds biomeIds;

    public TropicraftBeachLayer(TropicraftBiomeIds biomeIds) {
        this.biomeIds = biomeIds;
    }

    @Override
    public int sample(LayerRandomnessSource iNoiseRandom, int ne, int se, int sw, int nw, int center) {
        if (biomeIds.isOcean(center) && (!biomeIds.isOcean(ne) || !biomeIds.isOcean(se) || !biomeIds.isOcean(sw) || !biomeIds.isOcean(nw))) {
            return biomeIds.beach;
        }

        if (biomeIds.isRiver(center) && (!biomeIds.isRiver(ne) || !biomeIds.isRiver(se) || !biomeIds.isRiver(sw) || !biomeIds.isRiver(nw))) {
            return biomeIds.beach;
        }

        return center;
    }
}
