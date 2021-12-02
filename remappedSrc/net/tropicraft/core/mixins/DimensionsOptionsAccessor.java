package net.tropicraft.core.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.LinkedHashSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;

@Mixin(LevelStem.class)
public interface DimensionsOptionsAccessor {
    @Accessor("BASE_DIMENSIONS")
    public static LinkedHashSet<ResourceKey<LevelStem>> getBaseDimensions(){
        throw new AssertionError();
    }

    @Accessor("BASE_DIMENSIONS")
    @Mutable @Final
    public static void setBaseDimensions(LinkedHashSet<ResourceKey<LevelStem>> keys){
        throw new AssertionError();
    }


}
