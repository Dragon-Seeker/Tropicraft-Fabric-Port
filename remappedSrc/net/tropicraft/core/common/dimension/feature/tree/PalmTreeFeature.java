package net.tropicraft.core.common.dimension.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.tropicraft.core.common.block.TropicraftCoconutBlock;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public abstract class PalmTreeFeature extends Feature<NoneFeatureConfiguration> {

    public PalmTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    protected SaplingBlock getSapling() {
        return TropicraftBlocks.PALM_SAPLING;
    }
    
    protected final BlockState getLeaf() {
        return TropicraftBlocks.PALM_LEAVES.defaultBlockState();
    }
    
    protected final BlockState getLog() {
        return TropicraftBlocks.PALM_LOG.defaultBlockState();
    }

    protected void placeLeaf(final LevelSimulatedRW world, int x, int y, int z) {
        this.placeLeaf(world, new BlockPos(x, y, z));
    }

    protected void placeLeaf(final LevelSimulatedRW world, BlockPos pos) {
        // From FoliagePlacer
        if (TreeFeature.validTreePos(world, pos)) {
            setBlock(world, pos, getLeaf());
        }
    }

    protected void placeLog(final LevelSimulatedRW world, int x, int y, int z) {
        this.placeLog(world, new BlockPos(x, y, z));
    }

    protected void placeLog(final LevelSimulatedRW world, BlockPos pos) {
        if (TreeFeature.isFree(world, pos)) {
            setBlock(world, pos, getLog());
        }
    }

    private static final Direction[] DIRECTIONS = ArrayUtils.removeElement(Direction.values(), Direction.UP);
    public static void spawnCoconuts(LevelSimulatedRW world, BlockPos pos, Random random, int chance, BlockState leaf) {
        final BlockState coconut = TropicraftBlocks.COCONUT.defaultBlockState();
        for (Direction d : DIRECTIONS) {
            BlockPos pos2 = pos.relative(d);
            if (random.nextInt(chance) == 0 && TreeFeature.isAirOrLeaves(world, pos2)) {
                world.setBlock(pos2, coconut.setValue(TropicraftCoconutBlock.FACING, d.getOpposite()), 3);
            }
        }
    }
}
