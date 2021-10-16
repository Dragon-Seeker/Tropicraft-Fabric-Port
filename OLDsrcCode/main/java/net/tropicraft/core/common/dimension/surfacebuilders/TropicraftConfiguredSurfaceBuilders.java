package net.tropicraft.core.common.dimension.surfacebuilders;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import net.tropicraft.Constants;
import net.tropicraft.core.common.block.TropicSand;
import net.tropicraft.core.common.registry.TropicraftBlocks;

public final class TropicraftConfiguredSurfaceBuilders {
    private static final Lazy<BlockState> PURIFIED_SAND = new Lazy<>(() -> TropicraftBlocks.PURIFIED_SAND.getDefaultState());
    private static final Lazy<BlockState> UNDERWATER_PURIFIED_SAND = new Lazy<>(() -> PURIFIED_SAND.get().with(TropicSand.UNDERWATER, true));

    public static ConfiguredSurfaceBuilder<?> tropics;
    public static ConfiguredSurfaceBuilder<?> sandy;

    public static void ConfiguredSurfaceBuildersRegister() {


        TernarySurfaceConfig landConfig = new TernarySurfaceConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState());
        TernarySurfaceConfig sandyConfig = new TernarySurfaceConfig(PURIFIED_SAND.get(), PURIFIED_SAND.get(), UNDERWATER_PURIFIED_SAND.get());
        TernarySurfaceConfig sandyUnderwaterConfig = new TernarySurfaceConfig(UNDERWATER_PURIFIED_SAND.get(), UNDERWATER_PURIFIED_SAND.get(), UNDERWATER_PURIFIED_SAND.get());

        TropicsSurfaceBuilder.Config tropicsConfig = new TropicsSurfaceBuilder.Config(landConfig, sandyConfig, sandyUnderwaterConfig);

        tropics = register("tropics", TropicraftSurfaceBuilders.TROPICS, tropicsConfig);
        sandy = register("sandy", TropicraftSurfaceBuilders.UNDERWATER,
                new UnderwaterSurfaceBuilder.Config(sandyConfig, landConfig, sandyUnderwaterConfig)
        );
    }


    public static <C extends SurfaceConfig, S extends SurfaceBuilder<C>> ConfiguredSurfaceBuilder<?> register(String id, S surfaceBuilder, C config) {
        //return this.worldgen.register(new Identifier(Constants.MODID, id), surfaceBuilder.withConfig(config));
        return Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(Constants.MODID, id), surfaceBuilder.withConfig(config));
    }

}
