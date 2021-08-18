package net.tropicraftFabric.common.dimension.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.tropicraftFabric.common.registry.TropicraftBlocks;

import java.util.Random;

import static net.tropicraftFabric.common.dimension.feature.TropicraftFeatureUtil.goesBeyondWorldSize;

public class UndergrowthFeature extends Feature<DefaultFeatureConfig> {
    private static final int LARGE_BUSH_CHANCE = 5;

    public UndergrowthFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }
    
    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random rand, BlockPos pos, DefaultFeatureConfig config) {
        int size = 2;
        if (rand.nextInt(LARGE_BUSH_CHANCE) == 0) {
            size = 3;
        }
        
        if (!isValidPosition(world, pos)) {
            return false;
        }

        if (goesBeyondWorldSize(world, pos.getY(), size)) {
            return false;
        }

        if (!TropicraftFeatureUtil.isSoil(world, pos.down())) {
            return false;
        }

        world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), 3);
        setBlockState(world, pos, TropicraftBlocks.MAHOGANY_LOG.getDefaultState());

        int count = 0;

        for (int round = 0; round < 64; ++round) {
            BlockPos posTemp = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (isValidPosition(world, posTemp) && posTemp.getY() < 255) {
                for (int y = posTemp.getY(); y < posTemp.getY() + size; y++) {
                    int bushWidth = size - (y - posTemp.getY());
                    for (int x = posTemp.getX() - bushWidth; x < posTemp.getX() + bushWidth; x++) {
                        int xVariance = x - posTemp.getX();
                        for (int z = posTemp.getZ() - bushWidth; z < posTemp.getZ() + bushWidth; z++) {
                            int zVariance = z - posTemp.getZ();
                            final BlockPos newPos = new BlockPos(x, y, z);
                            if ((Math.abs(xVariance) != bushWidth || Math.abs(zVariance) != bushWidth || rand.nextInt(2) != 0) && isValidPosition(world, newPos)) {
                                setBlockState(world, newPos, TropicraftBlocks.KAPOK_LEAVES.getDefaultState());
                            }
                        }
                    }
                }
                ++count;
            }
        }

        return count > 0;
    }
    
    protected boolean isValidPosition(ModifiableTestableWorld world, BlockPos pos) {
        return TreeFeature.isAirOrLeaves(world, pos) && !world.testBlockState(pos, Blocks.CAVE_AIR.getDefaultState()::equals);
    }
}
