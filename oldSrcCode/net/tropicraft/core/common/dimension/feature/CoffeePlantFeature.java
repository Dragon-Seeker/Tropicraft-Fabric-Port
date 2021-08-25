package net.tropicraft.core.common.dimension.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.Random;

public class CoffeePlantFeature extends Feature<DefaultFeatureConfig> {
    public static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.getDefaultState();
    public static final BlockState COFE = TropicraftBlocks.COFFEE_BUSH.withAge(6); //.withAge(6);
    public static final BlockState FARMLAND = Blocks.FARMLAND.getDefaultState();
    public static final BlockState WATER = Blocks.WATER.getDefaultState();

    public CoffeePlantFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        final BlockPos genPos = new BlockPos(
                (pos.getX() + random.nextInt(8)) - random.nextInt(8),
                pos.getY(),
                (pos.getZ() + random.nextInt(8)) - random.nextInt(8)
        );

        // Find a suitable place to generate
        if (!world.isAir(genPos) || world.getBlockState(genPos.down()).getBlock() != GRASS_BLOCK.getBlock() || world.isAir(genPos.down(2))) {
            return false;
        }

        Direction viableDirection = null;

        // Scan for potential water spot
        for (final Direction dir : Direction.Type.HORIZONTAL) {
            final int neighborx = genPos.getX() + dir.getOffsetX();
            final int neighborz = genPos.getZ() + dir.getOffsetZ();

            if (!world.isAir(new BlockPos(neighborx, pos.getY() - 1, neighborz))) {
                viableDirection = dir;
                break;
            }
        }

        if (viableDirection == null) {
            return false;
        }

        final BlockPos waterPos = new BlockPos(genPos.getX() + viableDirection.getOffsetX(), pos.getY() - 1, genPos.getZ() + viableDirection.getOffsetZ());
        world.setBlockState(waterPos, WATER, 3);
        world.setBlockState(genPos.down(), FARMLAND, 3);

        for (final Direction dir : Direction.Type.HORIZONTAL) {
            world.setBlockState(waterPos.offset(dir), GRASS_BLOCK, 3);
        }

        for (int i = 0; i < 3; ++i) {
            final BlockPos upPos = genPos.up(i);
            if (world.isAir(upPos)) {
                world.setBlockState(upPos, COFE, 3);
            } else {
                break;
            }
        }

        return true;
    }
}
