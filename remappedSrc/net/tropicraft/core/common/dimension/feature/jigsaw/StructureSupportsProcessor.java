package net.tropicraft.core.common.dimension.feature.jigsaw;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.tropicraft.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StructureSupportsProcessor extends CheatyStructureProcessor {

    public static final Codec<StructureSupportsProcessor> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.BOOL.optionalFieldOf("can_replace_land", false).forGetter(p -> p.canReplaceLand),
            ResourceLocation.CODEC.listOf().fieldOf("states_to_extend").forGetter(p -> new ArrayList<>(p.statesToExtend))
        ).apply(instance, StructureSupportsProcessor::new);
    });

    static final StructureProcessorType<StructureSupportsProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":structure_supports", () -> CODEC);
    
    private final boolean canReplaceLand;
    private final Set<ResourceLocation> statesToExtend;

    public StructureSupportsProcessor(boolean canReplaceLand, List<ResourceLocation> statesToExtend) {
        this.canReplaceLand = canReplaceLand;
        this.statesToExtend = new ObjectOpenHashSet<>(statesToExtend);
    }

    @Override
    public StructureBlockInfo processBlock(LevelReader world, BlockPos seedPos, BlockPos pos2, StructureBlockInfo originalInfo, StructureBlockInfo blockInfo, StructurePlaceSettings placement) {
        BlockPos pos = blockInfo.pos;

        blockInfo.state.getBlock();

        if (originalInfo.pos.getY() <= 1 && statesToExtend.contains(Registry.BLOCK.getKey(blockInfo.state.getBlock()))) {
            if (!canReplaceLand && !canPassThrough(world, pos)) {
                // Delete blocks that would generate inside land
                return null;
            }
            if (originalInfo.pos.getY() == 0) {
                // Don't generate blocks underneath solid land
                if (!canReplaceLand && !canPassThrough(world, pos.above())) {
                    return null;
                }
                BlockPos fencePos = pos.below();
                // Extend blocks at the bottom of a structure down to the ground
                while (canPassThrough(world, fencePos)) {
                    BlockState state = blockInfo.state;
                    if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
                        state = state.setValue(FenceBlock.WATERLOGGED, world.getBlockState(fencePos).getBlock() == Blocks.WATER);
                    }
                    setBlockState(world, fencePos, state);
                    fencePos = fencePos.below();
                }
            }
        }
        return blockInfo;
    }
    
    protected boolean canPassThrough(LevelReader world, BlockPos pos) {
        return isAirOrWater(world, pos) || world.getHeightmapPos(Types.WORLD_SURFACE_WG, pos).getY() < pos.getY();
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
