package net.tropicraftFabric.common.dimension.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class KoaVillageStructure extends JigsawFeature {
    public KoaVillageStructure(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, 0, true, true);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator generator, BiomeSource biomes, long seed, ChunkRandom random, int chunkX, int chunkZ, Biome biome, ChunkPos startChunkPos, StructurePoolFeatureConfig config) {
        BlockPos pos = new BlockPos((chunkX << 4) + 8, 0, (chunkZ << 4) + 8);
        return isValid(generator, pos.add(-4, 0, -4)) &&
                isValid(generator, pos.add(-4, 0, 4)) &&
                isValid(generator, pos.add(4, 0, 4)) &&
                isValid(generator, pos.add(4, 0, -4));
    }

    private boolean isValid(ChunkGenerator generator, BlockPos pos) {
        return generator.getHeight(pos.getX(), pos.getZ(), Heightmap.Type.WORLD_SURFACE_WG) == generator.getSeaLevel();
    }
}
