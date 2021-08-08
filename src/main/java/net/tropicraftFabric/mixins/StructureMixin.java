package net.tropicraftFabric.mixins;

import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.tropicraftFabric.mixinExtensions.StructureExtensions;
import org.jetbrains.annotations.Nullable;
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

@Mixin(Structure.class)
public abstract class StructureMixin implements StructureExtensions {
    @Shadow
    @Final
    private List<Structure.StructureEntityInfo> entities;

    @Unique
    @Override
    public List<Structure.StructureEntityInfo> tropic$getEntities() {
        return entities;
    }

    @Inject(at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/structure/Structure;spawnEntities(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockBox;Z)V"),
            method = "place(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;I)Z", cancellable = true)
    public void tropic$place(ServerWorldAccess worldAccess, BlockPos blockPos, BlockPos blockPos2, StructurePlacementData placementSettings, Random random, int i, CallbackInfoReturnable<Boolean> cir) {
        tropic$addEntitiesToWorld(worldAccess, blockPos, placementSettings);
        cir.setReturnValue(true);
    }

    //TODO: Issue
    @Redirect(method = "place(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;I)Z",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/Structure;process(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/List;)Ljava/util/List"))
    public List<Structure.StructureBlockInfo> tropic$proceesCall(WorldAccess world, BlockPos blockPos, BlockPos blockPos2, StructurePlacementData placementSettings, List<Structure.StructureBlockInfo> list, @Nullable Structure structure, CallbackInfoReturnable<Boolean> cir) {
        return tropic$process(world, blockPos, blockPos2, placementSettings, list);//, structure);
        //cir.setReturnValue(true);
    }

    /*
    @Inject(at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/structure/Structure;process(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/util/math/BlockPos;Ljava/util/List;Z)V"),
            method = "place(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;I)Z", cancellable = true)
    public void tropic$proceesCallIDK(WorldAccess world, BlockPos blockPos, BlockPos blockPos2, StructurePlacementData placementSettings, List<Structure.StructureBlockInfo> list, @Nullable Structure structure,  CallbackInfoReturnable<Boolean> cir) {
        tropic$process(world, blockPos, blockPos2, placementSettings, list, structure);
        cir.setReturnValue(true);
    }

     */


}
