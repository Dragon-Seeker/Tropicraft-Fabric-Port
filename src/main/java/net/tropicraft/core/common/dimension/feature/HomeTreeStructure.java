package net.tropicraft.core.common.dimension.feature;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.FeaturePoolElement;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.tropicraft.Constants;
import net.tropicraft.core.common.dimension.feature.jigsaw.piece.NoRotateSingleJigsawPiece;
import net.tropicraft.core.mixins.StructureStartAccessor;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class HomeTreeStructure extends StructureFeature<StructurePoolFeatureConfig> {
    public HomeTreeStructure(Codec<StructurePoolFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator generator, BiomeSource biomes, long seed, ChunkRandom random, ChunkPos pos, Biome biome, ChunkPos startChunkPos, StructurePoolFeatureConfig config, HeightLimitView world) {
        BlockPos blockPos = new BlockPos((pos.x << 4) + 8, 0, (pos.z << 4) + 8);
        int centerY = generator.getHeight(blockPos.getX(), blockPos.getZ(), Heightmap.Type.WORLD_SURFACE_WG, world);
        return isValid(generator, blockPos.add(-4, 0, -4), centerY, world) &&
                isValid(generator, blockPos.add(-4, 0, 4), centerY, world) &&
                isValid(generator, blockPos.add(4, 0, 4), centerY, world) &&
                isValid(generator, blockPos.add(4, 0, -4), centerY, world);
    }

    private boolean isValid(ChunkGenerator generator, BlockPos pos, int startY, HeightLimitView world) {
        int y = generator.getHeight(pos.getX(), pos.getZ(), Heightmap.Type.WORLD_SURFACE_WG, world);
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
        public Start(StructureFeature<StructurePoolFeatureConfig> structure, ChunkPos chunkPos, int references, long seed) {
            super(structure, chunkPos, references, seed);
        }

        @Override
        public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager templates, ChunkPos pos, Biome biome, StructurePoolFeatureConfig config, HeightLimitView world) {
            BlockPos blockPos = new BlockPos(pos.x << 4, 0, pos.z << 4);
            StructurePoolBasedGenerator.generate(registryManager, config, Piece::new, chunkGenerator, templates, blockPos, (StructurePiecesHolder) this.children, this.random, true, true, world);
            this.setBoundingBoxFromChildrenCustom();
        }

        public BlockBox setBoundingBoxFromChildrenCustom() {
            int margin = 24; // Double vanilla's margin
            ((StructureStartAccessor)this).getBoundingBox().expand(margin);
            return super.setBoundingBoxFromChildren();
        }

        @Override
        protected void randomUpwardTranslation(int seaLevel, int i, Random random, int j) {
            int k = seaLevel - j;
            BlockBox blockBox = this.setBoundingBoxFromChildrenCustom();
            int l = blockBox.getBlockCountY() + i + 1;
            if (l < k) {
                l += random.nextInt(k - l);
            }

            int m = l - blockBox.getMaxY();
            this.translateUpward(m);
        }

        @Override
        protected void randomUpwardTranslation(Random random, int minY, int maxY) {
            BlockBox blockBox = this.setBoundingBoxFromChildrenCustom();
            int i = maxY - minY + 1 - blockBox.getBlockCountY();
            int k;
            if (i > 1) {
                k = minY + random.nextInt(i);
            } else {
                k = minY;
            }

            int l = k - blockBox.getMinY();
            this.translateUpward(l);
        }



    }

    public static class Piece extends PoolStructurePiece {
        public Piece(StructureManager templates, StructurePoolElement piece, BlockPos pos, int groundLevelDelta, BlockRotation rotation, BlockBox bounds) {
            super(templates, piece, pos, groundLevelDelta, rotation, bounds);
        }
        /*
        public Piece(StructureManager templates, NbtCompound data) {
            super(templates, data);
        }

         */

        public Piece(ServerWorld serverWorld, NbtCompound nbtCompound) {
            super(serverWorld, nbtCompound);
        }

        @Override
        public BlockBox getBoundingBox() {
            if (this.poolElement instanceof FeaturePoolElement) {
                BlockBox ret = super.getBoundingBox();
                //TODO: May not work, Possible need to change back to: new BlockBox(ret);
                ret = new BlockBox(ret.getCenter());
                ret.expand(32);

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
