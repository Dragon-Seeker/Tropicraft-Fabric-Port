package net.tropicraftFabric.common.dimension.feature.jigsaw;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldView;
import net.tropicraftFabric.Constants;

public class StructureVoidProcessor extends StructureProcessor {
    static final StructureProcessorType<StructureVoidProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":structure_void", () -> Codec.unit(new StructureVoidProcessor()));

    @Override
    public StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos p_230386_3_, StructureBlockInfo originalInfo, StructureBlockInfo blockInfo, StructurePlacementData placementSettings) {
        if (blockInfo.state.getBlock() == Blocks.STRUCTURE_VOID) {
            return new StructureBlockInfo(blockInfo.pos, Blocks.AIR.getDefaultState(), blockInfo.tag);
        }
        return blockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
