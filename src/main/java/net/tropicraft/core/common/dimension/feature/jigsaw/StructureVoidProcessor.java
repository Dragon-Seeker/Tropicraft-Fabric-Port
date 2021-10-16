package net.tropicraft.core.common.dimension.feature.jigsaw;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.tropicraft.Constants;

public class StructureVoidProcessor extends StructureProcessor {
    static final StructureProcessorType<StructureVoidProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":structure_void", () -> Codec.unit(new StructureVoidProcessor()));

    @Override
    public StructureBlockInfo processBlock(LevelReader world, BlockPos pos, BlockPos p_230386_3_, StructureBlockInfo originalInfo, StructureBlockInfo blockInfo, StructurePlaceSettings placementSettings) {
        if (blockInfo.state.getBlock() == Blocks.STRUCTURE_VOID) {
            return new StructureBlockInfo(blockInfo.pos, Blocks.AIR.defaultBlockState(), blockInfo.nbt);
        }
        return blockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
