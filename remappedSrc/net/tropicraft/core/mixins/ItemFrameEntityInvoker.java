package net.tropicraft.core.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemFrame.class)
public interface ItemFrameEntityInvoker {
    @Invoker("dropHeldStack")
    public void invokedropHeldStack(@Nullable Entity entity, boolean alwaysDrop);
}
