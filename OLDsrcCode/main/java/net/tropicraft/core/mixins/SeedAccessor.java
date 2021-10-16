package net.tropicraft.core.mixins;

import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkGenerator.class)
public interface SeedAccessor {
    @Accessor
    long getWorldSeed();
}
