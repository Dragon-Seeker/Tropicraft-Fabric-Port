package net.tropicraft.core.common.dimension.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.tropicraft.core.common.block.TropicraftCoconutBlock;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public abstract class PalmTreeFeature extends Feature<DefaultFeatureConfig> {

    public PalmTreeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    protected SaplingBlock getSapling() {
        return TropicraftBlocks.PALM_SAPLING;
    }
    
    protected final BlockState getLeaf() {
        return TropicraftBlocks.PALM_LEAVES.getDefaultState();
    }
    
    protected final BlockState getLog() {
        return TropicraftBlocks.PALM_LOG.getDefaultState();
    }

    protected void placeLeaf(final ModifiableTestableWorld world, int x, int y, int z) {
        this.placeLeaf(world, new BlockPos(x, y, z));
    }

    protected void placeLeaf(final ModifiableTestableWorld world, BlockPos pos) {
        // From FoliagePlacer
        if (TreeFeature.canReplace(world, pos)) {
            setBlockState(world, pos, getLeaf());
        }
    }

    protected void placeLog(final ModifiableTestableWorld world, int x, int y, int z) {
        this.placeLog(world, new BlockPos(x, y, z));
    }

    protected void placeLog(final ModifiableTestableWorld world, BlockPos pos) {
        if (TreeFeature.canTreeReplace(world, pos)) {
            setBlockState(world, pos, getLog());
        }
    }

    private static final Direction[] DIRECTIONS = ArrayUtils.removeElement(Direction.values(), Direction.UP);
    public static void spawnCoconuts(ModifiableTestableWorld world, BlockPos pos, Random random, int chance, BlockState leaf) {
        final BlockState coconut = TropicraftBlocks.COCONUT.getDefaultState();
        for (Direction d : DIRECTIONS) {
            BlockPos pos2 = pos.offset(d);
            if (random.nextInt(chance) == 0 && TreeFeature.isAirOrLeaves(world, pos2)) {
                world.setBlockState(pos2, coconut.with(TropicraftCoconutBlock.FACING, d.getOpposite()), 3);
            }
        }
    }
}
