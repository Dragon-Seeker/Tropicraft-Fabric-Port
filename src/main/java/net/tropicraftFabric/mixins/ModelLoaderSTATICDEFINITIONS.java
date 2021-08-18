package net.tropicraftFabric.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ModelLoader.class)
public interface ModelLoaderSTATICDEFINITIONS {
    @Accessor("STATIC_DEFINITIONS")
    public static Map<Identifier, StateManager<Block, BlockState>> getStaticDimensions() {
        throw new AssertionError();
    }

    @Accessor("STATIC_DEFINITIONS")
    public static void setStaticDimensions(Map<Identifier, StateManager<Block, BlockState>> staticDefintions) {
        throw new AssertionError();
    }
}
