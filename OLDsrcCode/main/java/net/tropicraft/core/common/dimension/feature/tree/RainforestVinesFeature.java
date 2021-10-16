package net.tropicraft.core.common.dimension.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.tropicraft.core.common.dimension.feature.config.RainforestVinesConfig;

import java.util.Random;

public class RainforestVinesFeature extends Feature<RainforestVinesConfig> {

    private static final Direction[] DIRECTIONS = Direction.values();

    public RainforestVinesFeature(Codec<RainforestVinesConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<RainforestVinesConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random rand = context.getRandom();
        BlockPos pos = context.getOrigin();
        RainforestVinesConfig config = context.getConfig();

        BlockPos.Mutable mutablePos = pos.mutableCopy();

        int maxY = Math.min(pos.getY() + config.height, world.getLogicalHeight());
        for (int y = pos.getY(); y < maxY; ++y) {
            for (int i = 0; i < config.rollsPerY; i++) {
                mutablePos.set(pos);
                mutablePos.move(rand.nextInt(config.xzSpread * 2) - config.xzSpread, 0, rand.nextInt(config.xzSpread * 2) - config.xzSpread);
                mutablePos.setY(y);
                if (world.isAir(mutablePos)) {
                    for (Direction direction : DIRECTIONS) {
                        mutablePos.move(direction);
                        BlockState attaching = world.getBlockState(mutablePos);
                        if ((attaching.getBlock() == Blocks.GRASS_BLOCK && rand.nextInt(4) == 0) || attaching.isIn(BlockTags.LEAVES)) {
                            if (direction != Direction.DOWN && VineBlock.shouldConnectTo(world, mutablePos, direction)) {
                                mutablePos.move(direction.getOpposite());
                                int len = rand.nextInt(3) + 2;
                                for (int j = 0; j < len && world.isAir(mutablePos); j++) {
                                    world.setBlockState(mutablePos, Blocks.VINE.getDefaultState().with(VineBlock.getFacingProperty(direction), true), 2);
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
