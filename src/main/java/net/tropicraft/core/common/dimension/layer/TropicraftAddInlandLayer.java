package net.tropicraft.core.common.dimension.layer;

import net.minecraft.world.biome.layer.type.DiagonalCrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public final class TropicraftAddInlandLayer implements DiagonalCrossSamplingLayer {
    private final int chance;
    private final TropicraftBiomeIds biomeIds;

    TropicraftAddInlandLayer(int chance, TropicraftBiomeIds biomeIds) {
        this.chance = chance;
        this.biomeIds = biomeIds;
    }

    @Override
    public int sample(LayerRandomnessSource random, int ne, int se, int sw, int nw, int center) {
        if (biomeIds.isLand(nw) && biomeIds.isLand(sw) && biomeIds.isLand(ne) && biomeIds.isLand(se) && biomeIds.isLand(center) && random.nextInt(chance) == 0) {
            return biomeIds.land;
        }

        return center;
    }
}
