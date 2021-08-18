package net.tropicraftFabric.common.dimension.feature;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.FeaturePoolElement;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.dimension.feature.jigsaw.piece.NoRotateSingleJigsawPiece;

public class HomeTreeStructure extends StructureFeature<StructurePoolFeatureConfig> {
    public HomeTreeStructure(Codec<StructurePoolFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator generator, BiomeSource biomes, long seed, ChunkRandom random, int chunkX, int chunkZ, Biome biome, ChunkPos startChunkPos, StructurePoolFeatureConfig config) {
        BlockPos pos = new BlockPos((chunkX << 4) + 8, 0, (chunkZ << 4) + 8);
        int centerY = generator.getHeight(pos.getX(), pos.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
        return isValid(generator, pos.add(-4, 0, -4), centerY) &&
                isValid(generator, pos.add(-4, 0, 4), centerY) &&
                isValid(generator, pos.add(4, 0, 4), centerY) &&
                isValid(generator, pos.add(4, 0, -4), centerY);
    }

    private boolean isValid(ChunkGenerator generator, BlockPos pos, int startY) {
        int y = generator.getHeight(pos.getX(), pos.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
        return y >= generator.getSeaLevel()
                && Math.abs(y - startY) < 10
                && y < 150
                && y > generator.getSeaLevel() + 2;
    }

    @Override
    public StructureStartFactory<StructurePoolFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    private static final StructurePieceType TYPE = StructurePieceType.register(Piece::new, Constants.MODID + ":home_tree");

    public static class Start extends StructureStart<StructurePoolFeatureConfig> {
        public Start(StructureFeature<StructurePoolFeatureConfig> structure, int chunkX, int chunkZ, BlockBox boundingBox, int references, long seed) {
            super(structure, chunkX, chunkZ, boundingBox, references, seed);
        }

        @Override
        public void init(DynamicRegistryManager registries, ChunkGenerator generator, StructureManager templates, int chunkX, int chunkZ, Biome biome, StructurePoolFeatureConfig config) {
            BlockPos pos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
            StructurePoolBasedGenerator.method_30419(registries, config, Piece::new, generator, templates, pos, this.children, this.random, true, true);
            this.setBoundingBoxFromChildren();
        }

        @Override
        protected void setBoundingBoxFromChildren() {
            super.setBoundingBoxFromChildren();
            int margin = 24; // Double vanilla's margin
            this.boundingBox.minX -= margin;
            this.boundingBox.minY -= margin;
            this.boundingBox.minZ -= margin;
            this.boundingBox.maxX += margin;
            this.boundingBox.maxY += margin;
            this.boundingBox.maxZ += margin;
        }
    }

    public static class Piece extends PoolStructurePiece {
        public Piece(StructureManager templates, StructurePoolElement piece, BlockPos pos, int groundLevelDelta, BlockRotation rotation, BlockBox bounds) {
            super(templates, piece, pos, groundLevelDelta, rotation, bounds);
        }

        public Piece(StructureManager templates, NbtCompound data) {
            super(templates, data);
        }

        @Override
        public BlockBox getBoundingBox() {
            if (this.poolElement instanceof FeaturePoolElement) {
                BlockBox ret = super.getBoundingBox();
                ret = new BlockBox(ret);
                ret.minX -= 32;
                ret.minY -= 32;
                ret.minZ -= 32;
                ret.maxX += 32;
                ret.maxY += 32;
                ret.maxZ += 32;
            }
            return super.getBoundingBox();
        }

        @Override
        public BlockRotation getRotation() {
            if (this.poolElement instanceof NoRotateSingleJigsawPiece) {
                return BlockRotation.NONE;
            }
            return super.getRotation();
        }

        @Override
        public StructurePieceType getType() {
            return TYPE;
        }
    }
}
