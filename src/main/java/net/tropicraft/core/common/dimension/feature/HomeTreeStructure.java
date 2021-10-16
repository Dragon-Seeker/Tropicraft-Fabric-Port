package net.tropicraft.core.common.dimension.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.FeaturePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.tropicraft.Constants;
import net.tropicraft.core.common.dimension.feature.jigsaw.piece.NoRotateSingleJigsawPiece;
import net.tropicraft.core.mixins.StructureStartAccessor;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class HomeTreeStructure extends StructureFeature<JigsawConfiguration> {
    public HomeTreeStructure(Codec<JigsawConfiguration> codec) {
        super(codec);
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator generator, BiomeSource biomes, long seed, WorldgenRandom random, ChunkPos pos, Biome biome, ChunkPos startChunkPos, JigsawConfiguration config, LevelHeightAccessor world) {
        BlockPos blockPos = new BlockPos((pos.x << 4) + 8, 0, (pos.z << 4) + 8);
        int centerY = generator.getBaseHeight(blockPos.getX(), blockPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, world);
        return isValid(generator, blockPos.offset(-4, 0, -4), centerY, world) &&
                isValid(generator, blockPos.offset(-4, 0, 4), centerY, world) &&
                isValid(generator, blockPos.offset(4, 0, 4), centerY, world) &&
                isValid(generator, blockPos.offset(4, 0, -4), centerY, world);
    }

    private boolean isValid(ChunkGenerator generator, BlockPos pos, int startY, LevelHeightAccessor world) {
        int y = generator.getBaseHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, world);
        return y >= generator.getSeaLevel()
                && Math.abs(y - startY) < 10
                && y < 150
                && y > generator.getSeaLevel() + 2;
    }

    @Override
    public StructureStartFactory<JigsawConfiguration> getStartFactory() {
        return Start::new;
    }

    private static final StructurePieceType TYPE = StructurePieceType.setPieceId(Piece::new, Constants.MODID + ":home_tree");

    public static class Start extends StructureStart<JigsawConfiguration> {
        public Start(StructureFeature<JigsawConfiguration> structure, ChunkPos chunkPos, int references, long seed) {
            super(structure, chunkPos, references, seed);
        }

        @Override
        public void generatePieces(RegistryAccess registryManager, ChunkGenerator chunkGenerator, StructureManager templates, ChunkPos pos, Biome biome, JigsawConfiguration config, LevelHeightAccessor world) {
            BlockPos blockPos = new BlockPos(pos.x << 4, 0, pos.z << 4);
            JigsawPlacement.addPieces(registryManager, config, Piece::new, chunkGenerator, templates, blockPos, this, this.random, true, true, world);

            this.getBoundingBox();
            this.setBoundingBoxFromChildrenCustom();
        }

        public BoundingBox setBoundingBoxFromChildrenCustom() {
            int margin = 24; // Double vanilla's margin
            ((StructureStartAccessor)this).getCachedBoundingBox().inflate(margin);
            return super.getBoundingBox();
        }

        @Override
        protected void moveBelowSeaLevel(int seaLevel, int i, Random random, int j) {
            int k = seaLevel - j;
            BoundingBox blockBox = this.setBoundingBoxFromChildrenCustom();
            int l = blockBox.getYSpan() + i + 1;
            if (l < k) {
                l += random.nextInt(k - l);
            }

            int m = l - blockBox.maxY();
            this.offsetPiecesVertically(m);
        }

        @Override
        protected void moveInsideHeights(Random random, int minY, int maxY) {
            BoundingBox blockBox = this.setBoundingBoxFromChildrenCustom();
            int i = maxY - minY + 1 - blockBox.getYSpan();
            int k;
            if (i > 1) {
                k = minY + random.nextInt(i);
            } else {
                k = minY;
            }

            int l = k - blockBox.minY();
            this.offsetPiecesVertically(l);
        }



    }

    public static class Piece extends PoolElementStructurePiece {
        public Piece(StructureManager templates, StructurePoolElement piece, BlockPos pos, int groundLevelDelta, Rotation rotation, BoundingBox bounds) {
            super(templates, piece, pos, groundLevelDelta, rotation, bounds);
        }
        /*
        public Piece(StructureManager templates, NbtCompound data) {
            super(templates, data);
        }

         */

        public Piece(ServerLevel serverWorld, CompoundTag nbtCompound) {
            super(serverWorld, nbtCompound);
        }

        @Override
        public BoundingBox getBoundingBox() {
            if (this.element instanceof FeaturePoolElement) {
                BoundingBox ret = super.getBoundingBox();
                //TODO: May not work, Possible need to change back to: new BlockBox(ret);
                ret = new BoundingBox(ret.getCenter());
                ret.inflate(32);

            }
            return super.getBoundingBox();
        }

        @Override
        public Rotation getRotation() {
            if (this.element instanceof NoRotateSingleJigsawPiece) {
                return Rotation.NONE;
            }
            return super.getRotation();
        }

        @Override
        public StructurePieceType getType() {
            return TYPE;
        }
    }
}
