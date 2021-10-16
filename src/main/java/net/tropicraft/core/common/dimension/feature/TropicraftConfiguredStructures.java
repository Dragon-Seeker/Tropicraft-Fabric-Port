package net.tropicraft.core.common.dimension.feature;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
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
        return Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Constants.MODID, id), configure.apply(structure));
    }

    public static <S extends StructureFeature<JigsawConfiguration>> ConfiguredStructureFeature<?, ?> register(String id, S structure, StructureTemplatePool templatePool, int maxDepth) {
        return register(id, structure, s -> s.configured(new JigsawConfiguration(() -> templatePool, maxDepth)));
    }

}
