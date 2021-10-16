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
import org.jetbrains.annotations.Nullable;

public class AirToCaveAirProcessor extends StructureProcessor {

    public static final Codec<AirToCaveAirProcessor> CODEC = Codec.unit(new AirToCaveAirProcessor());

    static final StructureProcessorType<AirToCaveAirProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":air_to_cave_air", () -> CODEC);
    
    @Override
    @Nullable
    public StructureBlockInfo processBlock(LevelReader world, BlockPos pos, BlockPos pos2, StructureBlockInfo originalInfo, StructureBlockInfo blockInfo, StructurePlaceSettings placementSettingsIn) {
        if (blockInfo.state.getBlock() == Blocks.AIR) {
            return new StructureBlockInfo(blockInfo.pos, Blocks.CAVE_AIR.defaultBlockState(), blockInfo.nbt);
        }
        return blockInfo;
        //super.process();
    }



    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
