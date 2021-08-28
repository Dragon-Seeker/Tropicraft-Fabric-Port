package net.tropicraft.core.mixins;

import net.minecraft.block.AbstractBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.tropicraft.core.mixinExtensions.BlockStateExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockstateInjection implements BlockStateExtension {

    //Lnet/minecraft/structure/Structure;spawnEntities(
    // Lnet/minecraft/world/ServerWorldAccess;
    // Lnet/minecraft/util/math/BlockPos;
    // Lnet/minecraft/util/BlockMirror;
    // Lnet/minecraft/util/BlockRotation;
    // Lnet/minecraft/util/math/BlockPos;
    // Lnet/minecraft/util/math/BlockBox;Z)V

    //Lnet/minecraft/block/AbstractBlock$AbstractBlockState;updateNeighbors(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;II)V
    @Inject(method = "updateNeighbors(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;II)V", at = @At(value = "HEAD"), cancellable = true)
    void inject_updateNeighbors(WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth, CallbackInfo cir) {
        if(currentBlockCheckPalm() == true){
            diagonalupdateNeighbors(world, pos, flags, maxUpdateDepth);
        }
    }

}
