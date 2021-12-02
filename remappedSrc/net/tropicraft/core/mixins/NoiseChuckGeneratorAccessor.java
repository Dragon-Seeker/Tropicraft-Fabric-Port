package net.tropicraft.core.mixins;

import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NoiseBasedChunkGenerator.class)
public interface NoiseChuckGeneratorAccessor {
    @Accessor("densityNoise")
    public void setDensityNoise(PerlinNoise densityNoise);
}
