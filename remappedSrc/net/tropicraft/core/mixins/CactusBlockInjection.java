package net.tropicraft.core.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public class CactusBlockInjection {
    @Inject(method = "canPlaceAt", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    void tc_canPlantOnTop(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState floor = world.getBlockState(pos.below());

        if (floor.is(TropicraftBlocks.MINERAL_SAND) || floor.is(TropicraftBlocks.CORAL_SAND) || floor.is(TropicraftBlocks.PURIFIED_SAND) || floor.is(TropicraftBlocks.PACKED_PURIFIED_SAND) || floor.is(TropicraftBlocks.FOAMY_SAND) || floor.is(TropicraftBlocks.VOLCANIC_SAND)) {
            cir.setReturnValue(true);
        }

    }
}
