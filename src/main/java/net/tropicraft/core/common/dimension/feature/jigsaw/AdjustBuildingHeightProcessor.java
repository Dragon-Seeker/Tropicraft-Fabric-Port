package net.tropicraft.core.common.dimension.feature.jigsaw;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.Structure;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.Structure.StructureEntityInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldView;
import net.tropicraft.Constants;

public class AdjustBuildingHeightProcessor extends StructurePassProcessor  {
    public static final Codec<AdjustBuildingHeightProcessor> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                Codec.INT.optionalFieldOf("base", 126).forGetter(c -> c.base)
        ).apply(instance, AdjustBuildingHeightProcessor::new);
    });

    static final StructureProcessorType<AdjustBuildingHeightProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":adjust_building_height", () -> CODEC);
    
    private final int base;

    public AdjustBuildingHeightProcessor(int base) {
        this.base = base;
    }

    @Override
    public StructureBlockInfo process(WorldView worldReaderIn, BlockPos seedPos, BlockPos p, StructureBlockInfo p_215194_3_, StructureBlockInfo blockInfo, StructurePlacementData placementSettingsIn, Structure template) {
        if (seedPos.getY() < base) {
            return new StructureBlockInfo(blockInfo.pos.up(), blockInfo.state, blockInfo.tag);
        }
        return blockInfo;
    }

    @Override
    public StructureEntityInfo processEntity(WorldView world, BlockPos seedPos, StructureEntityInfo rawEntityInfo, StructureEntityInfo entityInfo, StructurePlacementData placementSettings, Structure template) {
        if (seedPos.getY() < base) {
            return new StructureEntityInfo(entityInfo.pos.add(0, 1, 0), entityInfo.blockPos.up(), entityInfo.tag);
        }
        return entityInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
