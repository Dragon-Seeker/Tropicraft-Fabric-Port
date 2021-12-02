package net.tropicraft.core.common.dimension.surfacebuilders;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;
import net.tropicraft.Constants;
import net.tropicraft.core.common.block.TropicSand;
import net.tropicraft.core.common.registry.TropicraftBlocks;

public final class TropicraftConfiguredSurfaceBuilders {
    private static final LazyLoadedValue<BlockState> PURIFIED_SAND = new LazyLoadedValue<>(() -> TropicraftBlocks.PURIFIED_SAND.defaultBlockState());
    private static final LazyLoadedValue<BlockState> UNDERWATER_PURIFIED_SAND = new LazyLoadedValue<>(() -> PURIFIED_SAND.get().setValue(TropicSand.UNDERWATER, true));

    public static ConfiguredSurfaceBuilder<?> tropics;
    public static ConfiguredSurfaceBuilder<?> sandy;

    public static void ConfiguredSurfaceBuildersRegister() {


        SurfaceBuilderBaseConfiguration landConfig = new SurfaceBuilderBaseConfiguration(Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.DIRT.defaultBlockState(), Blocks.STONE.defaultBlockState());
        SurfaceBuilderBaseConfiguration sandyConfig = new SurfaceBuilderBaseConfiguration(PURIFIED_SAND.get(), PURIFIED_SAND.get(), UNDERWATER_PURIFIED_SAND.get());
        SurfaceBuilderBaseConfiguration sandyUnderwaterConfig = new SurfaceBuilderBaseConfiguration(UNDERWATER_PURIFIED_SAND.get(), UNDERWATER_PURIFIED_SAND.get(), UNDERWATER_PURIFIED_SAND.get());

        TropicsSurfaceBuilder.Config tropicsConfig = new TropicsSurfaceBuilder.Config(landConfig, sandyConfig, sandyUnderwaterConfig);

        tropics = register("tropics", TropicraftSurfaceBuilders.TROPICS, tropicsConfig);
        sandy = register("sandy", TropicraftSurfaceBuilders.UNDERWATER,
                new UnderwaterSurfaceBuilder.Config(sandyConfig, landConfig, sandyUnderwaterConfig)
        );
    }


    public static <C extends SurfaceBuilderConfiguration, S extends SurfaceBuilder<C>> ConfiguredSurfaceBuilder<?> register(String id, S surfaceBuilder, C config) {
        //return this.worldgen.register(new Identifier(Constants.MODID, id), surfaceBuilder.withConfig(config));
        return Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(Constants.MODID, id), surfaceBuilder.configured(config));
    }

}
