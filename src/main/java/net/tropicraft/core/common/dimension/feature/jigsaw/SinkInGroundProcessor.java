package net.tropicraft.core.common.dimension.feature.jigsaw;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftBlocks;

public class SinkInGroundProcessor extends CheatyStructureProcessor {
    public static final Codec<SinkInGroundProcessor> CODEC = Codec.unit(new SinkInGroundProcessor());

    static final StructureProcessorType<SinkInGroundProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":sink_in_ground", () -> CODEC);

    @Override
    public StructureBlockInfo processBlock(LevelReader worldReaderIn, BlockPos pos, BlockPos pos2, StructureBlockInfo originalBlockInfo, StructureBlockInfo blockInfo, StructurePlaceSettings placement) {
        pos = blockInfo.pos;

        if (originalBlockInfo.pos.getY() == 0) {
            if (!isAirOrWater(worldReaderIn, pos)) {
                return null;
            }
            return blockInfo;
        }
        
        // Get height of the ground at this spot
        BlockPos groundCheck = worldReaderIn.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos);
        // y == 2, we're above the path, remove fence blocks that are above sea level or next to some other block
        if (originalBlockInfo.pos.getY() == 2 && originalBlockInfo.state.getBlock() == TropicraftBlocks.BAMBOO_FENCE) {
            if (groundCheck.getY() > 127 || !isAirOrWater(worldReaderIn, pos.below(2))) {
                return null;
            }
            for (int i = 0; i < 4; i++) {
                if (!worldReaderIn.isEmptyBlock(pos.relative(Direction.from2DDataValue(i)))) {
                    return null;
                }
            }
        }
        
        // If above sea level, sink into the ground by one block
        if (groundCheck.getY() > 127) {
            // Convert slabs to bundles when they are over land
            if (!isAirOrWater(worldReaderIn, pos.below()) && originalBlockInfo.state.getBlock() == TropicraftBlocks.THATCH_SLAB) {
                blockInfo = new StructureBlockInfo(pos, TropicraftBlocks.THATCH_BUNDLE.defaultBlockState(), null);
            }
            
            // Only sink solid blocks, or blocks that are above air/water -- delete all others
            if (Block.isShapeFullBlock(blockInfo.state.getShape(worldReaderIn, pos.below())) || isAirOrWater(worldReaderIn, pos.below())) {
                return new StructureBlockInfo(pos.below(), blockInfo.state, blockInfo.nbt);
            }
            return null;
        }
        
        removeObstructions(worldReaderIn, pos.above(), pos.above(2));

        return blockInfo;
    }
    
    private void removeObstructions(LevelReader world, BlockPos... positions) {
        for (BlockPos pos : positions) {
            BlockState current = world.getBlockState(pos);
            if (current.is(BlockTags.LEAVES) || current.is(BlockTags.LOGS)) {
                setBlockState(world, pos, Blocks.AIR.defaultBlockState());
            }
        }
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
