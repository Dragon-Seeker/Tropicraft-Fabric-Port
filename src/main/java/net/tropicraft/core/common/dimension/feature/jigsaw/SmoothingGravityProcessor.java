package net.tropicraft.core.common.dimension.feature.jigsaw;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.Structure;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.GravityStructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Direction.AxisDirection;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.WorldView;
import net.tropicraft.Constants;

public class SmoothingGravityProcessor extends PathStructureProcessor {

    public static final Codec<SmoothingGravityProcessor> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                Type.CODEC.fieldOf("heightmap").forGetter(p -> p.heightmap),
                Codec.INT.fieldOf("offset").forGetter(p -> p.offset)
        ).apply(instance, SmoothingGravityProcessor::new);
    });

    static final StructureProcessorType<SmoothingGravityProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":smooth_gravity", () -> CODEC);

    private final Type heightmap;
    private final int offset;
    private final GravityStructureProcessor baseline;

    public SmoothingGravityProcessor(Type heightmap, int offset) {
        super();
        this.heightmap = heightmap;
        this.offset = offset;
        this.baseline = new GravityStructureProcessor(heightmap, offset);
    }

    @Override
    public StructureBlockInfo process(WorldView world, BlockPos seedPos, BlockPos pos2, StructureBlockInfo originalBlockInfo, StructureBlockInfo blockInfo, StructurePlacementData placementSettingsIn, Structure template) {
        Axis pathDir = getPathDirection(seedPos, blockInfo, placementSettingsIn, template);
        if (pathDir == null) {
            pathDir = Axis.X; // Better than nothing
        }
        BlockPos pos = blockInfo.pos;
        BlockPos posForward = pos.offset(Direction.get(AxisDirection.POSITIVE, pathDir));
        BlockPos posBackward = pos.offset(Direction.get(AxisDirection.NEGATIVE, pathDir));
        int heightForward = world.getTopY(heightmap, posForward.getX(), posForward.getZ()) + offset;
        int heightBackward = world.getTopY(heightmap, posBackward.getX(), posBackward.getZ()) + offset;
        int height = world.getTopY(heightmap, pos.getX(), pos.getZ()) + offset;
        if (heightForward > height && heightBackward > height) {
            return new StructureBlockInfo(new BlockPos(pos.getX(), Math.min(heightForward, heightBackward), pos.getZ()), blockInfo.state, blockInfo.tag);
        }
        return baseline.process(world, seedPos, pos2, originalBlockInfo, blockInfo, placementSettingsIn);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }
}
