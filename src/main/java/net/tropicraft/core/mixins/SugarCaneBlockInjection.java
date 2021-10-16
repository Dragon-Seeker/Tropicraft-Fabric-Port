package net.tropicraft.core.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockInjection {
    @Inject(at = @At(value = "HEAD"), method = "canSurvive", cancellable = true)
    void tc_canPlantOnTop(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState floor = world.getBlockState(pos.below());

        if (floor.is(TropicraftBlocks.MINERAL_SAND) || floor.is(TropicraftBlocks.CORAL_SAND) || floor.is(TropicraftBlocks.PURIFIED_SAND) || floor.is(TropicraftBlocks.PACKED_PURIFIED_SAND) || floor.is(TropicraftBlocks.FOAMY_SAND) || floor.is(TropicraftBlocks.VOLCANIC_SAND)) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                boolean isWaterAround = world.getFluidState(pos.below().relative(direction)).is(FluidTags.WATER);
                boolean isFrostedIceAround = world.getBlockState(pos.below().relative(direction)).is(Blocks.FROSTED_ICE);
                if (isWaterAround && isFrostedIceAround) continue;
                cir.setReturnValue(true);
            }
        }
    }
}
