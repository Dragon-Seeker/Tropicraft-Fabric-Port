package net.tropicraft.core.mixins;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.LinkedHashSet;
import java.util.Set;

@Mixin(DimensionOptions.class)
public interface DimensionsOptionsAccessor {
    @Accessor("BASE_DIMENSIONS")
    public static Set<RegistryKey<DimensionOptions>> getBaseDimensions(){
        throw new AssertionError();
    }

    @Accessor("BASE_DIMENSIONS")
    @Mutable @Final
    public static void setBaseDimensions(Set<RegistryKey<DimensionOptions>> keys){
        throw new AssertionError();
    }


}
