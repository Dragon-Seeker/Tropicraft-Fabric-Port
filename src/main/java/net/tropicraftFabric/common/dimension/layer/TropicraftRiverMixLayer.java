package net.tropicraftFabric.common.dimension.layer;

import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;

public final class TropicraftRiverMixLayer implements MergingLayer, IdentityCoordinateTransformer {
    private final TropicraftBiomeIds biomeIds;

    public TropicraftRiverMixLayer(TropicraftBiomeIds biomeIds) {
        this.biomeIds = biomeIds;
    }

    @Override
    public int sample(LayerRandomnessSource random, LayerSampler parent1, LayerSampler parent2, int x, int y) {
        final int biome = parent1.sample(transformX(x), transformZ(y));
        final int river = parent2.sample(transformX(x), transformZ(y));

        if (!biomeIds.isOcean(biome)) {
            if (biomeIds.isRiver(river)) {
                return river;
            }
        }

        return biome;
    }
}
