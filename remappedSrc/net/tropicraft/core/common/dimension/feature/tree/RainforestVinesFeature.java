package net.tropicraft.core.common.dimension.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.tropicraft.core.common.dimension.feature.config.RainforestVinesConfig;

import java.util.Random;

public class RainforestVinesFeature extends Feature<RainforestVinesConfig> {

    private static final Direction[] DIRECTIONS = Direction.values();

    public RainforestVinesFeature(Codec<RainforestVinesConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos pos, RainforestVinesConfig config) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();

        int maxY = Math.min(pos.getY() + config.height, world.getHeight());
        for (int y = pos.getY(); y < maxY; ++y) {
            for (int i = 0; i < config.rollsPerY; i++) {
                mutablePos.set(pos);
                mutablePos.move(rand.nextInt(config.xzSpread * 2) - config.xzSpread, 0, rand.nextInt(config.xzSpread * 2) - config.xzSpread);
                mutablePos.setY(y);
                if (world.isEmptyBlock(mutablePos)) {
                    for (Direction direction : DIRECTIONS) {
                        mutablePos.move(direction);
                        BlockState attaching = world.getBlockState(mutablePos);
                        if ((attaching.getBlock() == Blocks.GRASS_BLOCK && rand.nextInt(4) == 0) || attaching.is(BlockTags.LEAVES)) {
                            if (direction != Direction.DOWN && VineBlock.isAcceptableNeighbour(world, mutablePos, direction)) {
                                mutablePos.move(direction.getOpposite());
                                int len = rand.nextInt(3) + 2;
                                for (int j = 0; j < len && world.isEmptyBlock(mutablePos); j++) {
                                    world.setBlock(mutablePos, Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(direction), true), 2);
                                    mutablePos.move(Direction.DOWN);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
