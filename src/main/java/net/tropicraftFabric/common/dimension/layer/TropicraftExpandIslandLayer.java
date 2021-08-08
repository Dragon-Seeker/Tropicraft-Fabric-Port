package net.tropicraftFabric.common.dimension.layer;

import net.minecraft.world.biome.layer.type.DiagonalCrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public final class TropicraftExpandIslandLayer implements DiagonalCrossSamplingLayer {
    private final TropicraftBiomeIds biomeIds;

    public TropicraftExpandIslandLayer(TropicraftBiomeIds biomeIds) {
        this.biomeIds = biomeIds;
    }

    @Override
    public int sample(LayerRandomnessSource random, int ne, int se, int sw, int nw, int center) {
        if (biomeIds.isOcean(center)) {
            final boolean isNorthEastOcean = biomeIds.isOcean(ne);
            final boolean isSouthEastOcean = biomeIds.isOcean(se);
            final boolean isSouthWestOcean = biomeIds.isOcean(sw);
            final boolean isNorthWestOcean = biomeIds.isOcean(nw);
            if (!isNorthWestOcean || !isSouthWestOcean || !isNorthEastOcean || !isSouthEastOcean) {
                int chance = 1;
                int result = biomeIds.land;

                if (!isNorthWestOcean && random.nextInt(chance++) == 0) {
                    result = nw;
                }

                if (!isSouthWestOcean && random.nextInt(chance++) == 0) {
                    result = sw;
                }

                if (!isNorthEastOcean && random.nextInt(chance++) == 0) {
                    result = ne;
                }

                if (!isSouthEastOcean && random.nextInt(chance++) == 0) {
                    result = se;
                }

                if (random.nextInt(3) == 0) {
                    return result;
                }

                return center;
            }
        }

//        if (center == 0) {
//            final boolean isNorthEastOcean = ne == 0;
//            final boolean isSouthEastOcean = se == 0;
//            final boolean isSouthWestOcean = sw == 0;
//            final boolean isNorthWestOcean = nw == 0;
//            if (!isNorthWestOcean || !isSouthWestOcean || !isNorthEastOcean || !isSouthEastOcean) {
//                int chance = 1;
//                int result = TropicraftLayerUtil.LAND_ID;
//
//                if (!isNorthWestOcean && random.random(chance++) == 0) {
//                    result = nw;
//                }
//
//                if (!isSouthWestOcean && random.random(chance++) == 0) {
//                    result = sw;
//                }
//
//                if (!isNorthEastOcean && random.random(chance++) == 0) {
//                    result = ne;
//                }
//
//                if (!isSouthEastOcean && random.random(chance++) == 0) {
//                    result = se;
//                }
//
//                if (random.random(3) == 0) {
//                    return result;
//                }
//
//                return TropicraftLayerUtil.OCEAN_ID;
//            }
//        }

        return center;
    }
}
