package net.tropicraft.core.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockInjection {
    @Inject(at = @At(value = "HEAD"), method = "canPlaceAt", cancellable = true)
    void tc_canPlantOnTop(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState floor = world.getBlockState(pos.down());

        if (floor.isOf(TropicraftBlocks.MINERAL_SAND) || floor.isOf(TropicraftBlocks.CORAL_SAND) || floor.isOf(TropicraftBlocks.PURIFIED_SAND) || floor.isOf(TropicraftBlocks.PACKED_PURIFIED_SAND) || floor.isOf(TropicraftBlocks.FOAMY_SAND) || floor.isOf(TropicraftBlocks.VOLCANIC_SAND)) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                boolean isWaterAround = world.getFluidState(pos.down().offset(direction)).isIn(FluidTags.WATER);
                boolean isFrostedIceAround = world.getBlockState(pos.down().offset(direction)).isOf(Blocks.FROSTED_ICE);
                if (isWaterAround && isFrostedIceAround) continue;
                cir.setReturnValue(true);
            }
        }
    }
}
