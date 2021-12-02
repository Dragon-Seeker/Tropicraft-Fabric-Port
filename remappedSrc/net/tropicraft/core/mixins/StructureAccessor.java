package net.tropicraft.core.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

@Mixin(StructureTemplate.class)
public interface StructureAccessor {
    @Invoker("getEntity")
    static Optional<Entity> getEntity(ServerLevelAccessor world, CompoundTag nbt) {
        throw new AssertionError();
    }
}
