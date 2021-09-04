package net.tropicraft.core.common.dimension.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.Random;
import java.util.function.Supplier;

import static net.tropicraft.core.common.dimension.feature.TropicraftFeatureUtil.goesBeyondWorldSize;
import static net.tropicraft.core.common.dimension.feature.TropicraftFeatureUtil.isBBAvailable;

public class EIHFeature extends Feature<DefaultFeatureConfig> {

    private static final Supplier<BlockState> EIH_STATE = () -> TropicraftBlocks.CHUNK.getDefaultState();
    private static final BlockState LAVA_STATE = Blocks.LAVA.getDefaultState();

    public EIHFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos pos = context.getOrigin();
        DefaultFeatureConfig config = context.getConfig();
        ChunkGenerator generator = context.getGenerator();


        byte height = 5;
        int i = pos.getX();
        int j = pos.getY() + 1;
        int k = pos.getZ();

        if (goesBeyondWorldSize(world, pos.getY(), height)) {
            return false;
        }

        if (!isBBAvailable(world, pos, height)) {
            return false;
        }

        if (!TropicraftFeatureUtil.isSoil(world, pos.down()) && world.getBlockState(pos.down()).getMaterial() != Material.AGGREGATE) {
            return false;
        }

        setBlock(world, i + 0, j + 0, k + 2, EIH_STATE.get());
        setBlock(world, i + 0, j + 0, k + 3, EIH_STATE.get());
        setBlock(world, i + 0, j + 0, k + 4, EIH_STATE.get());
        setBlock(world, i + -1, j + 0, k + 4, EIH_STATE.get());
        setBlock(world, i + -1, j + 0, k + 1, EIH_STATE.get());
        setBlock(world, i + -1, j + 0, k + 3, EIH_STATE.get());
        setBlock(world, i + -1, j + 1, k + 4, EIH_STATE.get());
        setBlock(world, i + 0, j + 1, k + 4, EIH_STATE.get());
        setBlock(world, i + -1, j + 1, k + 3, EIH_STATE.get());
        setBlock(world, i + 0, j + 1, k + 3, EIH_STATE.get());
        setBlock(world, i + 0, j + 1, k + 2, EIH_STATE.get());
        setBlock(world, i + -1, j + 1, k + 1, EIH_STATE.get());
        setBlock(world, i + -1, j + 1, k + 0, EIH_STATE.get());
        setBlock(world, i + 0, j + 2, k + 3, EIH_STATE.get());
        setBlock(world, i + -1, j + 2, k + 3, EIH_STATE.get());
        setBlock(world, i + 0, j + 2, k + 2, EIH_STATE.get());
        setBlock(world, i + -1, j + 2, k + 0, EIH_STATE.get());
        setBlock(world, i + -1, j + 3, k + 3, EIH_STATE.get());
        setBlock(world, i + 0, j + 3, k + 2, EIH_STATE.get());
        setBlock(world, i + 0, j + 3, k + 1, EIH_STATE.get());
        setBlock(world, i + 0, j + 3, k + 0, EIH_STATE.get());
        setBlock(world, i + 0, j + 4, k + 2, EIH_STATE.get());
        setBlock(world, i + -1, j + 3, k + -1, EIH_STATE.get());
        setBlock(world, i + 0, j + 3, k + -1, EIH_STATE.get());
        setBlock(world, i + -1, j + 2, k + -1, EIH_STATE.get());
        setBlock(world, i + 0, j + 4, k + -1, EIH_STATE.get());
        setBlock(world, i + -1, j + 4, k + 2, EIH_STATE.get());
        setBlock(world, i + -1, j + 4, k + -1, EIH_STATE.get());
        setBlock(world, i + -1, j + 5, k + -1, EIH_STATE.get());
        setBlock(world, i + 0, j + 5, k + -1, EIH_STATE.get());
        setBlock(world, i + -1, j + 5, k + 1, EIH_STATE.get());
        setBlock(world, i + -1, j + 5, k + 2, EIH_STATE.get());
        setBlock(world, i + -1, j + 3, k + 4, EIH_STATE.get());
        setBlock(world, i + -1, j + 4, k + 3, EIH_STATE.get());
        setBlock(world, i + 0, j + 6, k + -1, EIH_STATE.get());
        setBlock(world, i + 0, j + 6, k + 0, EIH_STATE.get());
        setBlock(world, i + -1, j + 6, k + -1, EIH_STATE.get());
        setBlock(world, i + -1, j + 6, k + 0, EIH_STATE.get());
        setBlock(world, i + -1, j + 6, k + 1, EIH_STATE.get());
        setBlock(world, i + 1, j + 5, k + 0, EIH_STATE.get());
        setBlock(world, i + 1, j + 5, k + 1, EIH_STATE.get());
        setBlock(world, i + 1, j + 4, k + 1, EIH_STATE.get());
        setBlock(world, i + 1, j + 4, k + 0, EIH_STATE.get());
        setBlock(world, i + 0, j + 2, k + 1, EIH_STATE.get());
        setBlock(world, i + 0, j + 2, k + 0, EIH_STATE.get());
        setBlock(world, i + 0, j + 1, k + 0, EIH_STATE.get());
        setBlock(world, i + 0, j + 0, k + 0, EIH_STATE.get());
        setBlock(world, i + -1, j + 0, k + 0, EIH_STATE.get());
        setBlock(world, i + 0, j + 6, k + 1, EIH_STATE.get());
        setBlock(world, i + 0, j + 5, k + 0, LAVA_STATE);
        setBlock(world, i + -1, j + 4, k + 0, LAVA_STATE);
        setBlock(world, i + -1, j + 5, k + 0, LAVA_STATE);
        setBlock(world, i + -1, j + 3, k + 0, LAVA_STATE);
        setBlock(world, i + -1, j + 4, k + 1, LAVA_STATE);
        setBlock(world, i + -1, j + 3, k + 1, LAVA_STATE);
        setBlock(world, i + -1, j + 2, k + 1, LAVA_STATE);
        setBlock(world, i + -1, j + 3, k + 2, LAVA_STATE);
        setBlock(world, i + -1, j + 2, k + 2, LAVA_STATE);
        setBlock(world, i + -1, j + 1, k + 2, LAVA_STATE);
        setBlock(world, i + -2, j + 3, k + 4, EIH_STATE.get());
        setBlock(world, i + -2, j + 3, k + 3, EIH_STATE.get());
        setBlock(world, i + -2, j + 2, k + 3, EIH_STATE.get());
        setBlock(world, i + -2, j + 1, k + 3, EIH_STATE.get());
        setBlock(world, i + -2, j + 1, k + 4, EIH_STATE.get());
        setBlock(world, i + -2, j + 0, k + 4, EIH_STATE.get());
        setBlock(world, i + -2, j + 0, k + 3, EIH_STATE.get());
        setBlock(world, i + -2, j + 0, k + 1, EIH_STATE.get());
        setBlock(world, i + -2, j + 0, k + 0, EIH_STATE.get());
        setBlock(world, i + -2, j + 1, k + 1, EIH_STATE.get());
        setBlock(world, i + -2, j + 1, k + 0, EIH_STATE.get());
        setBlock(world, i + -2, j + 2, k + 0, EIH_STATE.get());
        setBlock(world, i + -2, j + 2, k + -1, EIH_STATE.get());
        setBlock(world, i + -2, j + 3, k + -1, EIH_STATE.get());
        setBlock(world, i + -2, j + 4, k + -1, EIH_STATE.get());
        setBlock(world, i + -2, j + 5, k + -1, EIH_STATE.get());
        setBlock(world, i + -2, j + 6, k + -1, EIH_STATE.get());
        setBlock(world, i + -2, j + 6, k + 1, EIH_STATE.get());
        setBlock(world, i + -2, j + 6, k + 0, EIH_STATE.get());
        setBlock(world, i + -2, j + 5, k + 2, EIH_STATE.get());
        setBlock(world, i + -2, j + 5, k + 1, EIH_STATE.get());
        setBlock(world, i + -2, j + 4, k + 2, EIH_STATE.get());
        setBlock(world, i + -2, j + 4, k + 3, EIH_STATE.get());
        setBlock(world, i + -2, j + 5, k + 0, LAVA_STATE);
        setBlock(world, i + -2, j + 4, k + 0, LAVA_STATE);
        setBlock(world, i + -2, j + 3, k + 0, LAVA_STATE);
        setBlock(world, i + -2, j + 4, k + 1, LAVA_STATE);
        setBlock(world, i + -2, j + 3, k + 1, LAVA_STATE);
        setBlock(world, i + -2, j + 2, k + 1, LAVA_STATE);
        setBlock(world, i + -2, j + 3, k + 2, LAVA_STATE);
        setBlock(world, i + -2, j + 2, k + 2, LAVA_STATE);
        setBlock(world, i + -2, j + 1, k + 2, LAVA_STATE);
        setBlock(world, i + -3, j + 0, k + 0, EIH_STATE.get());
        setBlock(world, i + -3, j + 0, k + 2, EIH_STATE.get());
        setBlock(world, i + -3, j + 0, k + 3, EIH_STATE.get());
        setBlock(world, i + -3, j + 0, k + 4, EIH_STATE.get());
        setBlock(world, i + -3, j + 1, k + 4, EIH_STATE.get());
        setBlock(world, i + -3, j + 1, k + 3, EIH_STATE.get());
        setBlock(world, i + -3, j + 2, k + 3, EIH_STATE.get());
        setBlock(world, i + -3, j + 1, k + 0, EIH_STATE.get());
        setBlock(world, i + -3, j + 1, k + 2, EIH_STATE.get());
        setBlock(world, i + -3, j + 2, k + 2, EIH_STATE.get());
        setBlock(world, i + -3, j + 2, k + 1, EIH_STATE.get());
        setBlock(world, i + -3, j + 2, k + 0, EIH_STATE.get());
        setBlock(world, i + -3, j + 3, k + 2, EIH_STATE.get());
        setBlock(world, i + -3, j + 4, k + 2, EIH_STATE.get());
        setBlock(world, i + -3, j + 3, k + 1, EIH_STATE.get());
        setBlock(world, i + -3, j + 3, k + 0, EIH_STATE.get());
        setBlock(world, i + -3, j + 3, k + -1, EIH_STATE.get());
        setBlock(world, i + -3, j + 4, k + -1, EIH_STATE.get());
        setBlock(world, i + -3, j + 5, k + -1, EIH_STATE.get());
        setBlock(world, i + -3, j + 6, k + -1, EIH_STATE.get());
        setBlock(world, i + -3, j + 6, k + 0, EIH_STATE.get());
        setBlock(world, i + -3, j + 6, k + 1, EIH_STATE.get());
        setBlock(world, i + -4, j + 5, k + 0, EIH_STATE.get());
        setBlock(world, i + -4, j + 4, k + 0, EIH_STATE.get());
        setBlock(world, i + -4, j + 4, k + 1, EIH_STATE.get());
        setBlock(world, i + 0, j + 4, k + 0, LAVA_STATE);
        setBlock(world, i + 0, j + 4, k + 1, LAVA_STATE);
        setBlock(world, i + -3, j + 4, k + 0, LAVA_STATE);
        setBlock(world, i + -3, j + 4, k + 1, LAVA_STATE);
        setBlock(world, i + -3, j + 5, k + 0, LAVA_STATE);
        setBlock(world, i + -4, j + 5, k + 1, EIH_STATE.get());
        setBlock(world, i + -2, j + 1, k + -1, EIH_STATE.get());
        setBlock(world, i + -1, j + 1, k + -1, EIH_STATE.get());
        setBlock(world, i + -2, j + 0, k + -1, EIH_STATE.get());
        setBlock(world, i + -1, j + 0, k + -1, EIH_STATE.get());
        setBlock(world, i + -3, j + -1, k + 0, EIH_STATE.get());
        setBlock(world, i + -2, j + -1, k + 0, EIH_STATE.get());
        setBlock(world, i + -1, j + -1, k + 0, EIH_STATE.get());
        setBlock(world, i + 0, j + -1, k + 0, EIH_STATE.get());
        setBlock(world, i + -2, j + -1, k + 1, EIH_STATE.get());
        setBlock(world, i + -1, j + -1, k + 1, EIH_STATE.get());
        setBlock(world, i + -3, j + -1, k + 2, EIH_STATE.get());
        setBlock(world, i + 0, j + -1, k + 2, EIH_STATE.get());
        setBlock(world, i + -2, j + -1, k + 3, EIH_STATE.get());
        setBlock(world, i + -1, j + -1, k + 3, EIH_STATE.get());
        setBlock(world, i + -3, j + -2, k + 2, EIH_STATE.get());
        setBlock(world, i + -2, j + -2, k + 3, EIH_STATE.get());
        setBlock(world, i + -1, j + -2, k + 3, EIH_STATE.get());
        setBlock(world, i + 0, j + -2, k + 2, EIH_STATE.get());
        setBlock(world, i + -1, j + -2, k + 1, EIH_STATE.get());
        setBlock(world, i + -2, j + -2, k + 1, EIH_STATE.get());
        setBlock(world, i + -3, j + -2, k + 0, EIH_STATE.get());
        setBlock(world, i + -2, j + -2, k + 0, EIH_STATE.get());
        setBlock(world, i + -1, j + -2, k + 0, EIH_STATE.get());
        setBlock(world, i + 0, j + -2, k + 0, EIH_STATE.get());
        setBlock(world, i + 0, j + -3, k + 2, EIH_STATE.get());
        setBlock(world, i + -1, j + -3, k + 3, EIH_STATE.get());
        setBlock(world, i + -1, j + 0, k + 2, LAVA_STATE);
        setBlock(world, i + -2, j + 0, k + 2, LAVA_STATE);
        setBlock(world, i + -1, j + -1, k + 2, LAVA_STATE);
        setBlock(world, i + -2, j + -1, k + 2, LAVA_STATE);
        setBlock(world, i + -2, j + -2, k + 2, LAVA_STATE);
        setBlock(world, i + -1, j + -2, k + 2, LAVA_STATE);
        setBlock(world, i + -2, j + -3, k + 3, EIH_STATE.get());
        setBlock(world, i + -1, j + -3, k + 2, EIH_STATE.get());
        setBlock(world, i + -2, j + -3, k + 2, EIH_STATE.get());
        setBlock(world, i + -3, j + -3, k + 2, EIH_STATE.get());
        setBlock(world, i + -2, j + -3, k + 1, EIH_STATE.get());
        setBlock(world, i + -1, j + -3, k + 1, EIH_STATE.get());
        setBlock(world, i + -3, j + -3, k + 0, EIH_STATE.get());
        setBlock(world, i + -2, j + -3, k + 0, EIH_STATE.get());
        setBlock(world, i + -1, j + -3, k + 0, EIH_STATE.get());
        setBlock(world, i + 0, j + -3, k + 0, EIH_STATE.get());

        // Coords of the first eye
        final int eyeOneX = i;
        final int eyeOneY = j + 5;
        final int eyeOneZ = k + 1;

        // Coords of the second eye
        final int eyeTwoX = i - 3;
        final int eyeTwoY = j + 5;
        final int eyeTwoZ = k + 1;

        final int eyeRand = world.getRandom().nextInt(9);

        // Place eyes
        placeEye(world, eyeOneX, eyeOneY, eyeOneZ, eyeRand);
        placeEye(world, eyeTwoX, eyeTwoY, eyeTwoZ, eyeRand);

        return true;
    }
    
    private void setBlock(WorldAccess world, int i, int i1, int i2, final BlockState state) {
        world.setBlockState(new BlockPos(i, i1, i2), state, 3);
    }

    /**
     * Place an eye on the head
     * @param x xCoord
     * @param y yCoord
     * @param z zCoord
     */
    private void placeEye(WorldAccess world, int x, int y, int z, int eyeRand) {
        if (world.getRandom().nextInt(1000) == 0) {
            eyeRand = world.getRandom().nextInt(9);
        }

        BlockState blockState;
        switch (eyeRand) {
            case 0:
            case 5:
                blockState = Blocks.GLOWSTONE.getDefaultState();
                break;
            case 1:
                blockState = Blocks.OBSIDIAN.getDefaultState();
                break;
            case 2:
                blockState = Blocks.DIAMOND_BLOCK.getDefaultState();
                break;
            case 3:
                blockState = Blocks.IRON_BLOCK.getDefaultState();
                break;
            case 4:
                blockState = Blocks.GOLD_BLOCK.getDefaultState();
                break;
            case 6:
                blockState = TropicraftBlocks.AZURITE_BLOCK.getDefaultState();
                break;
            case 7:
                blockState = TropicraftBlocks.EUDIALYTE_BLOCK.getDefaultState();
                break;
            case 8:
                blockState = TropicraftBlocks.ZIRCON_BLOCK.getDefaultState();
                break;
            default:    // Should never get called, if so, redstone in tropics :o
                blockState = Blocks.REDSTONE_BLOCK.getDefaultState();
                break;
        }

        setBlockState(world, new BlockPos(x, y, z), blockState);
    }
}
