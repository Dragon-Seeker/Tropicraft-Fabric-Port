package net.tropicraft.core.mixins;

import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DyeColor.class)
public interface DyeColorAccessor {
    //TODO: FIX THIS LATER
    //@Accessor
    //int getColor();
}
