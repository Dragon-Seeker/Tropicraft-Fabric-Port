package net.tropicraftFabric.mixins;

import net.tropicraftFabric.common.registry.TropicraftBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlantBlock.class)
public class TropicraftPlantableMixins {
    @Inject(at = @At("RETURN"), method = "canPlantOnTop", cancellable = true)
    void tc_canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (floor.isOf(TropicraftBlocks.PURIFIED_SAND)) {
            cir.setReturnValue(true);
        }
    }
}
