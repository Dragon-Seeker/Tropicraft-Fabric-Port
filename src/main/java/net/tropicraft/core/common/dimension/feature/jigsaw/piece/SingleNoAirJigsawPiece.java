package net.tropicraft.core.common.dimension.feature.jigsaw.piece;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElementType;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorLists;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
//import net.minecraft.world.gen.feature.template.*;
import net.tropicraft.Constants;

import java.util.function.Function;
import java.util.function.Supplier;

public class SingleNoAirJigsawPiece extends SinglePoolElement {
    public static final Codec<SingleNoAirJigsawPiece> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(method_28882(), method_28880(), method_28883())
                .apply(instance, SingleNoAirJigsawPiece::new);
    });

    private static final StructurePoolElementType<SingleNoAirJigsawPiece> TYPE = StructurePoolElementType.register(Constants.MODID + ":single_no_air", CODEC);

    public SingleNoAirJigsawPiece(Either<Identifier, Structure> template, Supplier<StructureProcessorList> processors, StructurePool.Projection placementBehaviour) {
        super(template, processors, placementBehaviour);
    }

    public SingleNoAirJigsawPiece(Structure template) {
        super(template);
    }

    public static Function<StructurePool.Projection, SingleNoAirJigsawPiece> create(String id, StructureProcessorList processors) {
        return placementBehaviour -> new SingleNoAirJigsawPiece(Either.left(new Identifier(id)), () -> processors, placementBehaviour);
    }

    public static Function<StructurePool.Projection, SingleNoAirJigsawPiece> create(String id) {
        return create(id, StructureProcessorLists.EMPTY);
    }

    @Override
    public StructurePoolElementType<?> getType() {
        return TYPE;
    }

    @Override
    protected StructurePlacementData createPlacementData(BlockRotation rotation, BlockBox box, boolean b) {
        StructurePlacementData settings = super.createPlacementData(rotation, box, b);
        settings.removeProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
        settings.addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
        return settings;
    }
}
