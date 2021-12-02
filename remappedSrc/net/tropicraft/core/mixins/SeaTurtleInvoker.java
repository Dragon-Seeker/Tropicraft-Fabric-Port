package net.tropicraft.core.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Turtle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Turtle.class)
public interface SeaTurtleInvoker {
    @Invoker("getHomePos")
    BlockPos inokverGetHomePos();
}
