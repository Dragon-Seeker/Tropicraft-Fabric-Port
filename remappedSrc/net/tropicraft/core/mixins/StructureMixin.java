package net.tropicraft.core.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.tropicraft.core.mixinExtensions.StructureExtensions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(StructureTemplate.class)
public abstract class StructureMixin implements StructureExtensions {
    @Shadow
    @Final
    private List<StructureTemplate.StructureEntityInfo> entities;

    @Unique
    @Override
    public List<StructureTemplate.StructureEntityInfo> tropic$getEntities() {
        return entities;
    }

    @Inject(at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/structure/Structure;spawnEntities(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockBox;Z)V"),
            method = "place(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;I)Z", cancellable = true)
    public void tropic$place(ServerLevelAccessor worldAccess, BlockPos blockPos, BlockPos blockPos2, StructurePlaceSettings placementSettings, Random random, int i, CallbackInfoReturnable<Boolean> cir) {
        tropic$addEntitiesToWorld(worldAccess, blockPos, placementSettings);
        cir.setReturnValue(true);
    }

    //Lnet/minecraft/structure/Structure;process(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/List;)Ljava/util/List;
    //Lnet/minecraft/structure/Structure;process(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/List;)Ljava/util/List;


    @Redirect(method = "place(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/Structure;process(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/List;)Ljava/util/List;"))
    private List<StructureTemplate.StructureBlockInfo> tropic$procees(LevelAccessor world, BlockPos blockPos, BlockPos blockPos2, StructurePlaceSettings placementSettings, List<StructureTemplate.StructureBlockInfo> list) {
        return tropic$process(world, blockPos, blockPos2, placementSettings, list);//, structure);
        //cir.setReturnValue(true);
    }

    /*
    //TODO: Issue
    @Redirect(method = "place", at = @At(value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/structure/Structure;process(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/List;)Ljava/util/List"))
    public List<Structure.StructureBlockInfo> tropic$proceesCall(WorldAccess world, BlockPos blockPos, BlockPos blockPos2, StructurePlacementData placementSettings, List<Structure.StructureBlockInfo> list, CallbackInfoReturnable<Boolean> cir) {
        return tropic$process(world, blockPos, blockPos2, placementSettings, list);//, structure);
        //cir.setReturnValue(true);
    }

     */

    /*
    @Inject(at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/structure/Structure;process(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/util/math/BlockPos;Ljava/util/List;Z)V"),
            method = "place(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;I)Z", cancellable = true)
    public void tropic$proceesCallIDK(WorldAccess world, BlockPos blockPos, BlockPos blockPos2, StructurePlacementData placementSettings, List<Structure.StructureBlockInfo> list, @Nullable Structure structure,  CallbackInfoReturnable<Boolean> cir) {
        tropic$process(world, blockPos, blockPos2, placementSettings, list, structure);
        cir.setReturnValue(true);
    }

     */


}
