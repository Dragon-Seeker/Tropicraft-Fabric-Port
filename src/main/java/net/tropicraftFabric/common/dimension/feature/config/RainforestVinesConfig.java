package net.tropicraftFabric.common.dimension.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class RainforestVinesConfig implements FeatureConfig {
    public static final Codec<RainforestVinesConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                Codec.INT.fieldOf("height").forGetter(c -> c.height),
                Codec.INT.fieldOf("xz_spread").forGetter(c -> c.xzSpread),
                Codec.INT.fieldOf("rolls_per_y").forGetter(c -> c.rollsPerY)
        ).apply(instance, RainforestVinesConfig::new);
    });

    // TODO make home tree radius configurable
    public final int height;
    public final int xzSpread;
    public final int rollsPerY;
    
    public RainforestVinesConfig() {
        this(256, 4, 1);
    }

    public RainforestVinesConfig(final int height, final int xzSpread, final int rollsPerY) {
        this.height = height;
        this.xzSpread = xzSpread;
        this.rollsPerY = rollsPerY;
    }
}
