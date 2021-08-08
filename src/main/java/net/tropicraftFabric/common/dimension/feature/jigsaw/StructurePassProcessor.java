package net.tropicraftFabric.common.dimension.feature.jigsaw;

import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public abstract class StructurePassProcessor extends CheatyStructureProcessor{
    @Deprecated
    public Structure.StructureBlockInfo process(WorldView world, BlockPos seedPos, BlockPos pos2, Structure.StructureBlockInfo originalBlockInfo, Structure.StructureBlockInfo blockInfo, StructurePlacementData placementSettingsIn) {
        return blockInfo;
    }

    public Structure.StructureEntityInfo processEntity(WorldView world, BlockPos seedPos, Structure.StructureEntityInfo rawEntityInfo, Structure.StructureEntityInfo entityInfo, StructurePlacementData placementSettings, Structure template) {
        return entityInfo;
    }

    public Structure.StructureBlockInfo process(WorldView world, BlockPos seedPos, BlockPos pos2, Structure.StructureBlockInfo originalBlockInfo, Structure.StructureBlockInfo blockInfo, StructurePlacementData placementSettingsIn, Structure template) {
        return process(world, seedPos, pos2, originalBlockInfo, blockInfo, placementSettingsIn);
    }
}
