package net.tropicraft.core.common.dimension.feature.jigsaw;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldView;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpawnerProcessor extends StructureProcessor {
    public static final SpawnerProcessor IGUANA = new SpawnerProcessor(ImmutableList.of(TropicraftEntities.IGUANA));
    public static final SpawnerProcessor ASHEN = new SpawnerProcessor(ImmutableList.of(TropicraftEntities.ASHEN));
    public static final SpawnerProcessor EIH = new SpawnerProcessor(ImmutableList.of(TropicraftEntities.EIH));
    public static final SpawnerProcessor IGUANA_AND_ASHEN = new SpawnerProcessor(ImmutableList.of(TropicraftEntities.ASHEN, TropicraftEntities.IGUANA));

    public static final Codec<SpawnerProcessor> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                Registry.ENTITY_TYPE.listOf().fieldOf("entity_types").forGetter(p -> p.entityTypes)
        ).apply(instance, SpawnerProcessor::new);
    });

    public static final StructureProcessorType<SpawnerProcessor> TYPE = Registry.register(Registry.STRUCTURE_PROCESSOR, Constants.MODID + ":spawner_processor", () -> CODEC);

    private final List<EntityType<?>> entityTypes;

    public SpawnerProcessor(final List<EntityType<?>> entityTypes) {
        this.entityTypes = entityTypes;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }

    @Override
    @Nullable
    public Structure.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pos2, Structure.StructureBlockInfo originalBlockInfo, Structure.StructureBlockInfo blockInfo, StructurePlacementData settings) {
        final Block block = blockInfo.state.getBlock();

        if (block != Blocks.SPAWNER) {
            return blockInfo;
        } else {
            final NbtCompound tag = new NbtCompound();
            String typeName = Registry.ENTITY_TYPE.getId(entityTypes.get(0)).toString();

            tag.putString("id", Registry.ENTITY_TYPE.getId(entityTypes.get(0)).toString());

            blockInfo.tag.getCompound("SpawnData").putString("id", typeName);
            // TODO not working
            final NbtList list = blockInfo.tag.getList("SpawnPotentials", 9);
            for (int i = 0; i < list.size(); i++) {
                final NbtCompound nbt = list.getCompound(i);
                nbt.getCompound("Entity").putString("id", typeName);
            }

            return blockInfo;
        }
    }

}
