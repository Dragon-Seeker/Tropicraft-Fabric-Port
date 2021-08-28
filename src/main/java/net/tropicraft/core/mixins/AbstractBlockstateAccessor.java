package net.tropicraft.core.mixins;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;


@Mixin(AbstractBlock.AbstractBlockState.class)
public interface AbstractBlockstateAccessor {
    @Invoker("asBlockState")
        BlockState invokeAsBlockState();
}
