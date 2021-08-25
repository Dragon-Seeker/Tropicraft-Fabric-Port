package net.tropicraft.core.common.dimension.feature.jigsaw;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.WorldView;
import net.tropicraft.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StructureSupportsProcessor extends CheatyStructureProcessor {

    public static final Codec<StructureSupportsProcessor> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.BOOL.optionalFieldOf("can_replace_land", false).forGetter(p -> p.canReplaceLand),
            Identifier.CODEC.listOf().fieldOf("states_to_extend").forGetter(p -> new ArrayList<>(p.statesToExtend))
        ).apply(instance, StructureSupportsProcessor::new);
    });

    static final StructureProcessorType<StructureSupportsProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":structure_supports", () -> CODEC);
    
    private final boolean canReplaceLand;
    private final Set<Identifier> statesToExtend;

    public StructureSupportsProcessor(boolean canReplaceLand, List<Identifier> statesToExtend) {
        this.canReplaceLand = canReplaceLand;
        this.statesToExtend = new ObjectOpenHashSet<>(statesToExtend);
    }

    @Override
    public StructureBlockInfo process(WorldView world, BlockPos seedPos, BlockPos pos2, StructureBlockInfo originalInfo, StructureBlockInfo blockInfo, StructurePlacementData placement) {
        BlockPos pos = blockInfo.pos;

        blockInfo.state.getBlock();

        if (originalInfo.pos.getY() <= 1 && statesToExtend.contains(Registry.BLOCK.getId(blockInfo.state.getBlock()))) {
            if (!canReplaceLand && !canPassThrough(world, pos)) {
                // Delete blocks that would generate inside land
                return null;
            }
            if (originalInfo.pos.getY() == 0) {
                // Don't generate blocks underneath solid land
                if (!canReplaceLand && !canPassThrough(world, pos.up())) {
                    return null;
                }
                BlockPos fencePos = pos.down();
                // Extend blocks at the bottom of a structure down to the ground
                while (canPassThrough(world, fencePos)) {
                    BlockState state = blockInfo.state;
                    if (state.contains(Properties.WATERLOGGED)) {
                        state = state.with(FenceBlock.WATERLOGGED, world.getBlockState(fencePos).getBlock() == Blocks.WATER);
                    }
                    setBlockState(world, fencePos, state);
                    fencePos = fencePos.down();
                }
            }
        }
        return blockInfo;
    }
    
    protected boolean canPassThrough(WorldView world, BlockPos pos) {
        return isAirOrWater(world, pos) || world.getTopPosition(Type.WORLD_SURFACE_WG, pos).getY() < pos.getY();
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
