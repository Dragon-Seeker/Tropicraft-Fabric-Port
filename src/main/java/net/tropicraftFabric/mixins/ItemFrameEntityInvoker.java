package net.tropicraftFabric.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemFrameEntity.class)
public interface ItemFrameEntityInvoker {
    @Invoker("dropHeldStack")
    public void invokedropHeldStack(@Nullable Entity entity, boolean alwaysDrop);
}
