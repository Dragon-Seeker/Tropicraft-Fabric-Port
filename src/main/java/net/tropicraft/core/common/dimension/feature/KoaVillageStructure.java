package net.tropicraft.core.common.dimension.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class KoaVillageStructure extends JigsawFeature {
    public KoaVillageStructure(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, 0, true, true);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator generator, BiomeSource biomes, long seed, ChunkRandom random, ChunkPos pos, Biome biome, ChunkPos startChunkPos, StructurePoolFeatureConfig config, HeightLimitView world) {
        BlockPos posCreated = new BlockPos((pos.x << 4) + 8, 0, (pos.z << 4) + 8);
        return isValid(generator, posCreated.add(-4, 0, -4), world) &&
                isValid(generator, posCreated.add(-4, 0, 4), world) &&
                isValid(generator, posCreated.add(4, 0, 4), world) &&
                isValid(generator, posCreated.add(4, 0, -4),world);
    }

    private boolean isValid(ChunkGenerator generator, BlockPos pos, HeightLimitView world) {
        return generator.getHeight(pos.getX(), pos.getZ(), Heightmap.Type.WORLD_SURFACE_WG, world) == generator.getSeaLevel();
    }
}
