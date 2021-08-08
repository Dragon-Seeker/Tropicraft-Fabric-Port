package net.tropicraftFabric.common.dimension.feature;

import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.client.data.WorldgenDataConsumer;
import net.tropicraftFabric.common.dimension.feature.pools.TropicraftTemplatePools;

import java.util.function.Function;

public final class TropicraftConfiguredStructures {
    public final ConfiguredStructureFeature<?, ?> homeTree;
    public final ConfiguredStructureFeature<?, ?> koaVillage;

    public TropicraftConfiguredStructures(WorldgenDataConsumer<ConfiguredStructureFeature<?, ?>> worldgen, TropicraftTemplatePools templatePools) {
        Register structures = new Register(worldgen);

        this.homeTree = structures.register("home_tree", TropicraftFeatures.HOME_TREE, templatePools.homeTreeStarts, 7);
        this.koaVillage = structures.register("koa_village", TropicraftFeatures.KOA_VILLAGE, templatePools.koaTownCenters, 6);
    }

    static final class Register {
        private final WorldgenDataConsumer<ConfiguredStructureFeature<?, ?>> worldgen;

        Register(WorldgenDataConsumer<ConfiguredStructureFeature<?, ?>> worldgen) {
            this.worldgen = worldgen;
        }

        public <S extends StructureFeature<?>> ConfiguredStructureFeature<?, ?> register(String id, S structure, Function<S, ConfiguredStructureFeature<?, ?>> configure) {
            return this.worldgen.register(new Identifier(Constants.MODID, id), configure.apply(structure));
        }

        public <S extends StructureFeature<StructurePoolFeatureConfig>> ConfiguredStructureFeature<?, ?> register(String id, S structure, StructurePool templatePool, int maxDepth) {
            return this.register(id, structure, s -> s.configure(new StructurePoolFeatureConfig(() -> templatePool, maxDepth)));
        }
    }
}
