package net.tropicraftFabric.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.Structure;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(Structure.class)
public interface StructureAccessor {
    @Invoker("getEntity")
    static Optional<Entity> getEntity(ServerWorldAccess iServerWorld, NbtCompound compoundNBT) {
        throw new AssertionError();
    }
}
