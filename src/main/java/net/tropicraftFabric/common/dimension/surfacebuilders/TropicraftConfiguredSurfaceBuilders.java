package net.tropicraftFabric.common.dimension.surfacebuilders;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.client.data.WorldgenDataConsumer;
import net.tropicraftFabric.common.block.TropicSand;
import net.tropicraftFabric.common.registry.TropicraftBlocks;

public final class TropicraftConfiguredSurfaceBuilders {
    private static final Lazy<BlockState> PURIFIED_SAND = new Lazy<>(() -> TropicraftBlocks.PURIFIED_SAND.getDefaultState());
    private static final Lazy<BlockState> UNDERWATER_PURIFIED_SAND = new Lazy<>(() -> PURIFIED_SAND.get().with(TropicSand.UNDERWATER, true));

    public final ConfiguredSurfaceBuilder<?> tropics;
    public final ConfiguredSurfaceBuilder<?> sandy;

    public TropicraftConfiguredSurfaceBuilders(WorldgenDataConsumer<ConfiguredSurfaceBuilder<?>> worldgen) {
        Register surfaceBuilders = new Register(worldgen);

        TernarySurfaceConfig landConfig = new TernarySurfaceConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState());
        TernarySurfaceConfig sandyConfig = new TernarySurfaceConfig(PURIFIED_SAND.get(), PURIFIED_SAND.get(), UNDERWATER_PURIFIED_SAND.get());
        TernarySurfaceConfig sandyUnderwaterConfig = new TernarySurfaceConfig(UNDERWATER_PURIFIED_SAND.get(), UNDERWATER_PURIFIED_SAND.get(), UNDERWATER_PURIFIED_SAND.get());

        TropicsSurfaceBuilder.Config tropicsConfig = new TropicsSurfaceBuilder.Config(landConfig, sandyConfig, sandyUnderwaterConfig);

        this.tropics = surfaceBuilders.register("tropics", TropicraftSurfaceBuilders.TROPICS, tropicsConfig);
        this.sandy = surfaceBuilders.register("sandy", TropicraftSurfaceBuilders.UNDERWATER,
                new UnderwaterSurfaceBuilder.Config(sandyConfig, landConfig, sandyUnderwaterConfig)
        );
    }

    static final class Register {
        private final WorldgenDataConsumer<ConfiguredSurfaceBuilder<?>> worldgen;

        Register(WorldgenDataConsumer<ConfiguredSurfaceBuilder<?>> worldgen) {
            this.worldgen = worldgen;
        }

        public <C extends SurfaceConfig, S extends SurfaceBuilder<C>> ConfiguredSurfaceBuilder<?> register(String id, S surfaceBuilder, C config) {
            return this.worldgen.register(new Identifier(Constants.MODID, id), surfaceBuilder.withConfig(config));
        }
    }
}
