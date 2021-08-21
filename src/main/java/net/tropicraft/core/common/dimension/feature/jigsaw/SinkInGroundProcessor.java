package net.tropicraft.core.common.dimension.feature.jigsaw;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldView;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftBlocks;

public class SinkInGroundProcessor extends CheatyStructureProcessor {
    public static final Codec<SinkInGroundProcessor> CODEC = Codec.unit(new SinkInGroundProcessor());

    static final StructureProcessorType<SinkInGroundProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":sink_in_ground", () -> CODEC);

    @Override
    public StructureBlockInfo process(WorldView worldReaderIn, BlockPos pos, BlockPos pos2, StructureBlockInfo originalBlockInfo, StructureBlockInfo blockInfo, StructurePlacementData placement) {
        pos = blockInfo.pos;

        if (originalBlockInfo.pos.getY() == 0) {
            if (!isAirOrWater(worldReaderIn, pos)) {
                return null;
            }
            return blockInfo;
        }
        
        // Get height of the ground at this spot
        BlockPos groundCheck = worldReaderIn.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos);
        // y == 2, we're above the path, remove fence blocks that are above sea level or next to some other block
        if (originalBlockInfo.pos.getY() == 2 && originalBlockInfo.state.getBlock() == TropicraftBlocks.BAMBOO_FENCE) {
            if (groundCheck.getY() > 127 || !isAirOrWater(worldReaderIn, pos.down(2))) {
                return null;
            }
            for (int i = 0; i < 4; i++) {
                if (!worldReaderIn.isAir(pos.offset(Direction.fromHorizontal(i)))) {
                    return null;
                }
            }
        }
        
        // If above sea level, sink into the ground by one block
        if (groundCheck.getY() > 127) {
            // Convert slabs to bundles when they are over land
            if (!isAirOrWater(worldReaderIn, pos.down()) && originalBlockInfo.state.getBlock() == TropicraftBlocks.THATCH_SLAB) {
                blockInfo = new StructureBlockInfo(pos, TropicraftBlocks.THATCH_BUNDLE.getDefaultState(), null);
            }
            
            // Only sink solid blocks, or blocks that are above air/water -- delete all others
            if (Block.isShapeFullCube(blockInfo.state.getOutlineShape(worldReaderIn, pos.down())) || isAirOrWater(worldReaderIn, pos.down())) {
                return new StructureBlockInfo(pos.down(), blockInfo.state, blockInfo.tag);
            }
            return null;
        }
        
        removeObstructions(worldReaderIn, pos.up(), pos.up(2));

        return blockInfo;
    }
    
    private void removeObstructions(WorldView world, BlockPos... positions) {
        for (BlockPos pos : positions) {
            BlockState current = world.getBlockState(pos);
            if (current.isIn(BlockTags.LEAVES) || current.isIn(BlockTags.LOGS)) {
                setBlockState(world, pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
