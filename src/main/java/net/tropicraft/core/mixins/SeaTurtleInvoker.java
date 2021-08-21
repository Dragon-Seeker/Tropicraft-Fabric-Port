package net.tropicraft.core.mixins;

import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TurtleEntity.class)
public interface SeaTurtleInvoker {
    @Invoker("getHomePos")
    BlockPos inokverGetHomePos();
}
