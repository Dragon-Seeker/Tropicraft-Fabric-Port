package net.tropicraft.core.mixins;

import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StructureFeature.class)
public interface StructureFeatureInvoker {
    //accessible    method    net/minecraft/world/gen/feature/StructureFeature            register                     (Ljava/lang/String;Lnet/minecraft/world/gen/feature/StructureFeature;Lnet/minecraft/world/gen/GenerationStep$Feature;)Lnet/minecraft/world/gen/feature/StructureFeature;
    @Invoker("register")
    static <F extends StructureFeature<?>> F invokeRegister(String name, F structureFeature, GenerationStep.Feature step) {
        throw new AssertionError();
    }
}
