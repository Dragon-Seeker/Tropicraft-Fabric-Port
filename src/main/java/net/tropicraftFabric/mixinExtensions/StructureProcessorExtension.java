package net.tropicraftFabric.mixinExtensions;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public interface StructureProcessorExtension {

    @Deprecated
    default Structure.StructureBlockInfo process(WorldView world, BlockPos seedPos, BlockPos pos2, Structure.StructureBlockInfo originalBlockInfo, Structure.StructureBlockInfo blockInfo, StructurePlacementData placementSettingsIn) {
           return blockInfo;
        }

    default Structure.StructureEntityInfo processEntity(WorldView world, BlockPos seedPos, Structure.StructureEntityInfo rawEntityInfo, Structure.StructureEntityInfo entityInfo, StructurePlacementData placementSettings, Structure template) {
        return entityInfo;
    }

    default Structure.StructureBlockInfo process(WorldView world, BlockPos seedPos, BlockPos pos2, Structure.StructureBlockInfo originalBlockInfo, Structure.StructureBlockInfo blockInfo, StructurePlacementData placementSettingsIn, Structure template) {
        return process(world, seedPos, pos2, originalBlockInfo, blockInfo, placementSettingsIn);
    }
}
