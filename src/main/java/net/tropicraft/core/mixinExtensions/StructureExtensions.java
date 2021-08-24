package net.tropicraft.core.mixinExtensions;

import com.google.common.collect.Lists;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.tropicraft.core.common.dimension.feature.jigsaw.AdjustBuildingHeightProcessor;
import net.tropicraft.core.common.dimension.feature.jigsaw.SmoothingGravityProcessor;
import net.tropicraft.core.common.dimension.feature.jigsaw.SteepPathProcessor;
import net.tropicraft.core.mixins.StructureAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public interface StructureExtensions {
    List<Structure.StructureEntityInfo> tropic$getEntities();

    default Vec3d transformedVec3d(StructurePlacementData placementIn, Vec3d pos) {
        return Structure.transformAround(pos, placementIn.getMirror(), placementIn.getRotation(), placementIn.getPosition());
    }

    default List<Structure.StructureEntityInfo> tropic$processEntityInfos(@Nullable Structure structure, WorldAccess world, BlockPos blockPos, StructurePlacementData settings, List<Structure.StructureEntityInfo> infos) {
        List<Structure.StructureEntityInfo> list = Lists.newArrayList();
        for(Structure.StructureEntityInfo entityInfo : infos) {
            Vec3d pos = transformedVec3d(settings, entityInfo.pos).add(Vec3d.of(blockPos));
            BlockPos blockpos = net.minecraft.structure.Structure.transform(settings, entityInfo.blockPos).add(blockPos);
            Structure.StructureEntityInfo info = new Structure.StructureEntityInfo(pos, blockpos, entityInfo.nbt);
            for (StructureProcessor proc : settings.getProcessors()) {
                if(proc instanceof AdjustBuildingHeightProcessor){
                    info = ((AdjustBuildingHeightProcessor) proc).processEntity(world, blockPos, entityInfo, info, settings, structure);
                }

                else{
                    info = ((StructureProcessorExtension) proc).processEntity(world, blockPos, entityInfo, info, settings, structure);
                }

                if (info == null)
                    break;
            }
            if (info != null)
                list.add(info);
        }
        return list;
    }

    default void tropic$addEntitiesToWorld(ServerWorldAccess world, BlockPos blockPos, StructurePlacementData settings) {
        for(Structure.StructureEntityInfo Structure$entityinfo : tropic$processEntityInfos((Structure) this, world, blockPos, settings, this.tropic$getEntities())) {
            BlockPos blockpos = Structure.transformAround(Structure$entityinfo.blockPos, settings.getMirror(), settings.getRotation(), settings.getPosition()).add(blockPos);
            blockpos = Structure$entityinfo.blockPos;
            if (settings.getBoundingBox() == null || settings.getBoundingBox().contains(blockpos)) {
                NbtCompound compoundnbt = Structure$entityinfo.nbt.copy();
                Vec3d vector3d1 = Structure$entityinfo.pos;
                NbtList listnbt = new NbtList();
                listnbt.add(NbtDouble.of(vector3d1.x));
                listnbt.add(NbtDouble.of(vector3d1.y));
                listnbt.add(NbtDouble.of(vector3d1.z));
                compoundnbt.put("Pos", listnbt);
                compoundnbt.remove("UUID");
                StructureAccessor.getEntity(world, compoundnbt).ifPresent((entity) -> {
                    float f = entity.applyMirror(settings.getMirror());
                    f = f + (entity.getYaw() - entity.applyRotation(settings.getRotation()));
                    entity.refreshPositionAndAngles(vector3d1.x, vector3d1.y, vector3d1.z, f, entity.getPitch());
                    if (settings.method_27265() && entity instanceof MobEntity) {
                        ((MobEntity) entity).initialize(world, world.getLocalDifficulty(new BlockPos(vector3d1)), SpawnReason.STRUCTURE, (EntityData)null, compoundnbt);
                    }

                    world.spawnEntityAndPassengers(entity);
                });
            }
        }

    }


    default List<Structure.StructureBlockInfo> tropic$process(WorldAccess world, BlockPos pos, BlockPos blockPos, StructurePlacementData placementData, List<Structure.StructureBlockInfo> list){//, @Nullable Structure structure) {
        List<Structure.StructureBlockInfo> list2 = Lists.newArrayList();
        Iterator var6 = list.iterator();

        while(var6.hasNext()) {
            Structure.StructureBlockInfo structureBlockInfo = (Structure.StructureBlockInfo)var6.next();
            BlockPos blockPos2 = Structure.transform(placementData, structureBlockInfo.pos).add(pos);
            Structure.StructureBlockInfo structureBlockInfo2 = new Structure.StructureBlockInfo(blockPos2, structureBlockInfo.state, structureBlockInfo.nbt != null ? structureBlockInfo.nbt.copy() : null);

            for(Iterator iterator = placementData.getProcessors().iterator(); structureBlockInfo2 != null && iterator.hasNext();) {
                StructureProcessor processor = ((StructureProcessor)iterator.next());
                if(processor instanceof AdjustBuildingHeightProcessor) {
                    structureBlockInfo2 = ((AdjustBuildingHeightProcessor)processor).process(world, pos, blockPos, structureBlockInfo, structureBlockInfo2, placementData, (Structure) this);
                }

                else if(processor instanceof SmoothingGravityProcessor) {
                    structureBlockInfo2 = ((SmoothingGravityProcessor)processor).process(world, pos, blockPos, structureBlockInfo, structureBlockInfo2, placementData, (Structure) this);
                }

                else if(processor instanceof SteepPathProcessor) {
                    structureBlockInfo2 = ((SteepPathProcessor)processor).process(world, pos, blockPos, structureBlockInfo, structureBlockInfo2, placementData, (Structure)this );
                }

                else {
                    structureBlockInfo2 = processor.process(world, pos, blockPos, structureBlockInfo, structureBlockInfo2, placementData);
                }
            }

            if (structureBlockInfo2 != null) {
                list2.add(structureBlockInfo2);
            }
        }

        return list2;
    }
}
