package net.tropicraftFabric.common.dimension.feature.jigsaw;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.structure.Structure;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.AxisDirection;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldView;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.registry.TropicraftBlocks;
import org.jetbrains.annotations.Nullable;

public class SteepPathProcessor extends PathStructureProcessor  {
    public static final Codec<SteepPathProcessor> CODEC = Codec.unit(new SteepPathProcessor());

    static final StructureProcessorType<SteepPathProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":steep_path", () -> CODEC);



    @Override
    public StructureBlockInfo process(WorldView worldReaderIn, BlockPos seedPos, BlockPos pos2, StructureBlockInfo originalBlockInfo, StructureBlockInfo blockInfo, StructurePlacementData placementSettingsIn, Structure template) {
        BlockPos pos = blockInfo.pos;

        if (originalBlockInfo.pos.getY() != 1 || originalBlockInfo.state.getBlock() == TropicraftBlocks.BAMBOO_STAIRS) {
            return blockInfo;
        }

        Direction.Axis axis = getPathDirection(seedPos, blockInfo, placementSettingsIn, template);
        if (axis == null) {
            return blockInfo;
        }
        
        // If this is true, we are "bridging" upwards past an air gap, handles overhangs
        int bridgeTo = -1;
        
        BlockState ladder = null;
        for (AxisDirection axisDir : AxisDirection.values()) {
            Direction dir = Direction.get(axisDir, axis);
            // Detect an overhang by checking if the heightmap between spots differs by >2
            BlockPos nextHeight = worldReaderIn.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos.offset(dir)).down();
            if (nextHeight.getY() > pos.getY()) {
                ladder = getLadderState(dir);
                bridgeTo = nextHeight.getY();
            }
            if (ladder != null) {
                break;
            }
        }
        if (ladder == null) {
            return blockInfo; // Nothing to do here, we're on flat ground
        }
        // The facing the ladder stores is opposite to the direction it's placed (i.e. it faces "outward")
        Direction dir = ladder.get(LadderBlock.FACING).getOpposite();
        pos = pos.up();
        if (bridgeTo == pos.getY() && canPlaceLadderAt(worldReaderIn, pos.up(), dir) == null) {
            // If the next spot up can't support a ladder, this is a one block step, so place a stair block
            setBlockState(worldReaderIn, pos, TropicraftBlocks.THATCH_STAIRS.getDefaultState().with(StairsBlock.FACING, dir));
        } else {
            // Otherwise, place ladders upwards until we find air (bridging over an initial gap if required)
            while (bridgeTo >= pos.getY() || canPlaceLadderAt(worldReaderIn, pos, dir) != null) {
                setBlockState(worldReaderIn, pos, ladder);
                setBlockState(worldReaderIn, pos.offset(dir), TropicraftBlocks.THATCH_BUNDLE.getDefaultState());
                pos = pos.up();
            }
        }
        return blockInfo;
    }
    
    // Check that there is a solid block behind the ladder at this pos, and return the correct ladder state
    // Returns null if placement is not possible
    private BlockState canPlaceLadderAt(WorldView worldReaderIn, BlockPos pos, Direction dir) {
        BlockPos check = pos.offset(dir);
        BlockState state = worldReaderIn.getBlockState(check);
        if (!state.isAir()) {
            BlockState ladderState = getLadderState(dir);
            if (ladderState.canPlaceAt(worldReaderIn, pos)) {
                return ladderState;
            }
        }
        return null;
    }
    
    private BlockState getLadderState(Direction dir) {
        return TropicraftBlocks.BAMBOO_LADDER.getDefaultState().with(LadderBlock.FACING, dir.getOpposite());
    }

    @Nullable
    @Override
    public StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos blockPos, StructureBlockInfo structureBlockInfo, StructureBlockInfo structureBlockInfo2, StructurePlacementData structurePlacementData) {
        return structureBlockInfo2;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }

}
