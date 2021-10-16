package net.tropicraft.core.common.dimension.feature.jigsaw;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldView;
import net.tropicraft.Constants;
import org.jetbrains.annotations.Nullable;

public class AirToCaveAirProcessor extends StructureProcessor {

    public static final Codec<AirToCaveAirProcessor> CODEC = Codec.unit(new AirToCaveAirProcessor());

    static final StructureProcessorType<AirToCaveAirProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":air_to_cave_air", () -> CODEC);
    
    @Override
    @Nullable
    public StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pos2, StructureBlockInfo originalInfo, StructureBlockInfo blockInfo, StructurePlacementData placementSettingsIn) {
        if (blockInfo.state.getBlock() == Blocks.AIR) {
            return new StructureBlockInfo(blockInfo.pos, Blocks.CAVE_AIR.getDefaultState(), blockInfo.nbt);
        }
        return blockInfo;
        //super.process();
    }



    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
