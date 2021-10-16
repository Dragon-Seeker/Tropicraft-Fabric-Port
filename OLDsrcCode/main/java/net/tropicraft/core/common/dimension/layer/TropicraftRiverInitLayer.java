package net.tropicraft.core.common.dimension.layer;

import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public final class TropicraftRiverInitLayer implements IdentitySamplingLayer {
    public final TropicraftBiomeIds biomeIds;

    public TropicraftRiverInitLayer(TropicraftBiomeIds biomeIds) {
        this.biomeIds = biomeIds;
    }

    @Override
    public int sample(LayerRandomnessSource random, int center) {
        return biomeIds.isOcean(center) ? center : random.nextInt(4) + 1;
    }
/*
    @Override
    public int apply(INoiseRandom iNoiseRandom, int center) {
        //return iNoiseRandom.random(4) + 1;
        return TropicraftLayerUtil.isOcean(center) ? center : iNoiseRandom.random(4) + 1;
    }*/
}
