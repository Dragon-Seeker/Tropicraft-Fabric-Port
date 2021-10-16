package net.tropicraft.core.common.dimension.feature;

import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.tropicraft.Constants;
import net.tropicraft.core.common.dimension.feature.pools.TropicraftTemplatePools;

import java.util.function.Function;

public final class TropicraftConfiguredStructures {
    public static ConfiguredStructureFeature<?, ?> homeTree;
    public static ConfiguredStructureFeature<?, ?> koaVillage;

    public static void registerConfiguredStructures() {
        //Register structures = new Register(worldgen);

        homeTree = register("home_tree", TropicraftFeatures.HOME_TREE, TropicraftTemplatePools.homeTreeStarts, 7);
        koaVillage = register("koa_village", TropicraftFeatures.KOA_VILLAGE, TropicraftTemplatePools.koaTownCenters, 6);
    }

    public static <S extends StructureFeature<?>> ConfiguredStructureFeature<?, ?> register(String id, S structure, Function<S, ConfiguredStructureFeature<?, ?>> configure) {
        //return this.worldgen.register(new Identifier(Constants.MODID, id), configure.apply(structure));
        return Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new Identifier(Constants.MODID, id), configure.apply(structure));
    }

    public static <S extends StructureFeature<StructurePoolFeatureConfig>> ConfiguredStructureFeature<?, ?> register(String id, S structure, StructurePool templatePool, int maxDepth) {
        return register(id, structure, s -> s.configure(new StructurePoolFeatureConfig(() -> templatePool, maxDepth)));
    }

}
