package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Random;

public class TropicLeavesBlock extends LeavesBlock {
    public TropicLeavesBlock() {
        super(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
                .breakByTool(FabricToolTags.HOES)
                .breakByTool(FabricToolTags.SHEARS)
                .breakByHand(true)
                .strength(0.2f)
                .noOcclusion()
                .sound(SoundType.GRASS));

    }


    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        // ignore decay
    }




}
