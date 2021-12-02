package net.tropicraft.core.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

@Mixin(ModelBakery.class)
public interface ModelLoaderSTATICDEFINITIONS {
    @Accessor("STATIC_DEFINITIONS")
    public static Map<ResourceLocation, StateDefinition<Block, BlockState>> getStaticDimensions() {
        throw new AssertionError();
    }

    @Accessor("STATIC_DEFINITIONS")
    public static void setStaticDimensions(Map<ResourceLocation, StateDefinition<Block, BlockState>> staticDefintions) {
        throw new AssertionError();
    }
}
