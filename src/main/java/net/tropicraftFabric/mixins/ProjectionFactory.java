package net.tropicraftFabric.mixins;

import com.google.common.collect.ImmutableList;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StructurePool.Projection.class)
public interface ProjectionFactory {
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    static StructurePool.Projection newProjection(String internalName, int internalOrdinal, String id, ImmutableList<StructureProcessor> processors) {
        throw new AssertionError();
    }
}
