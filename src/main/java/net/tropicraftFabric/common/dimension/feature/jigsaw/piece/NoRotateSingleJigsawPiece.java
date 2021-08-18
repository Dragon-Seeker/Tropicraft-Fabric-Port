package net.tropicraftFabric.common.dimension.feature.jigsaw.piece;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.Structure;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElementType;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.tropicraftFabric.Constants;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class NoRotateSingleJigsawPiece extends SinglePoolElement {

    public static final Codec<NoRotateSingleJigsawPiece> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(method_28882(), method_28880(), method_28883())
                .apply(instance, NoRotateSingleJigsawPiece::new);
    });

    private static final StructurePoolElementType<NoRotateSingleJigsawPiece> TYPE = StructurePoolElementType.method_28885(Constants.MODID + ":single_no_rotate", CODEC);

    public NoRotateSingleJigsawPiece(Either<Identifier, Structure> template, Supplier<StructureProcessorList> processors, StructurePool.Projection placementBehaviour) {
        super(template, processors, placementBehaviour);
    }

    public NoRotateSingleJigsawPiece(Structure template) {
        super(template);
    }

    public static Function<StructurePool.Projection, NoRotateSingleJigsawPiece> createNoRotate(String id, StructureProcessorList processors) {
        return placementBehaviour -> new NoRotateSingleJigsawPiece(Either.left(new Identifier(id)), () -> processors, placementBehaviour);
    }

    @Override
    public StructurePoolElementType<?> getType() {
        return TYPE;
    }

    @Override
    protected StructurePlacementData createPlacementData(BlockRotation rotation, BlockBox box, boolean b) {
        return super.createPlacementData(BlockRotation.NONE, box, b);
    }

    @Override
    public void method_16756(WorldAccess worldIn, StructureBlockInfo p_214846_2_, BlockPos pos, BlockRotation rotationIn, Random rand, BlockBox p_214846_6_) {
        super.method_16756(worldIn, p_214846_2_, pos, BlockRotation.NONE, rand, p_214846_6_);
    }

    @Override
    public List<StructureBlockInfo> getDataStructureBlocks(StructureManager p_214857_1_, BlockPos p_214857_2_, BlockRotation p_214857_3_, boolean p_214857_4_) {
        return super.getDataStructureBlocks(p_214857_1_, p_214857_2_, BlockRotation.NONE, p_214857_4_);
    }

    @Override
    public BlockBox getBoundingBox(StructureManager templateManagerIn, BlockPos pos, BlockRotation rotationIn) {
        return super.getBoundingBox(templateManagerIn, pos, BlockRotation.NONE);
    }

    @Override
    public List<StructureBlockInfo> getStructureBlockInfos(StructureManager templateManager, BlockPos pos, BlockRotation rotation, Random random) {
        return super.getStructureBlockInfos(templateManager, pos, BlockRotation.NONE, random);
    }

    @Override
    public boolean generate(StructureManager templates, StructureWorldAccess world, StructureAccessor structures, ChunkGenerator generator, BlockPos pos, BlockPos pos2, BlockRotation rotation, BlockBox box, Random random, boolean b) {
        return super.generate(templates, world, structures, generator, pos, pos2, BlockRotation.NONE, box, random, b);
    }
}
