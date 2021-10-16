package net.tropicraft.core.common.dimension.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class KoaVillageStructure extends JigsawFeature {
    public KoaVillageStructure(Codec<JigsawConfiguration> codec) {
        super(codec, 0, true, true);
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator generator, BiomeSource biomes, long seed, WorldgenRandom random, ChunkPos pos, Biome biome, ChunkPos startChunkPos, JigsawConfiguration config, LevelHeightAccessor world) {
        BlockPos posCreated = new BlockPos((pos.x << 4) + 8, 0, (pos.z << 4) + 8);
        return isValid(generator, posCreated.offset(-4, 0, -4), world) &&
                isValid(generator, posCreated.offset(-4, 0, 4), world) &&
                isValid(generator, posCreated.offset(4, 0, 4), world) &&
                isValid(generator, posCreated.offset(4, 0, -4),world);
    }

    private boolean isValid(ChunkGenerator generator, BlockPos pos, LevelHeightAccessor world) {
        return generator.getBaseHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, world) == generator.getSeaLevel();
    }
}
