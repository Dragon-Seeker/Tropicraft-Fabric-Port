package net.tropicraftFabric.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.tropicraftFabric.common.block.TropicSand;
import net.tropicraftFabric.common.registry.TropicraftBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public class CactusBlockInjection {
    @Inject(method = "canPlaceAt", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    void tc_canPlantOnTop(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState floor = world.getBlockState(pos.down());

        if (floor.isOf(TropicraftBlocks.MINERAL_SAND) || floor.isOf(TropicraftBlocks.CORAL_SAND) || floor.isOf(TropicraftBlocks.PURIFIED_SAND) || floor.isOf(TropicraftBlocks.PACKED_PURIFIED_SAND) || floor.isOf(TropicraftBlocks.FOAMY_SAND) || floor.isOf(TropicraftBlocks.VOLCANIC_SAND)) {
            cir.setReturnValue(true);
        }

    }
}
