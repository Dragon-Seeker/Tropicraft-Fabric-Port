package net.tropicraft.core.common.dimension.layer;

import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public final class TropicsIslandLayer implements InitLayer {
    private final TropicraftBiomeIds biomeIds;

    public TropicsIslandLayer(TropicraftBiomeIds biomeIds) {
        this.biomeIds = biomeIds;
    }

    @Override
    public int sample(LayerRandomnessSource random, int x, int y) {
        // if (0, 0) is located here, place an island
        if (x == 0 && y == 0) {
            return this.biomeIds.land;
        }

        return random.nextInt(3) == 0 ? this.biomeIds.land : this.biomeIds.ocean;
    }
}
