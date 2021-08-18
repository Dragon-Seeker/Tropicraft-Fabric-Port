package net.tropicraftFabric.mixins;

import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NoiseChunkGenerator.class)
public interface NoiseChuckGeneratorAccessor {
    @Accessor("densityNoise")
    public void setDensityNoise(OctavePerlinNoiseSampler densityNoise);
}
