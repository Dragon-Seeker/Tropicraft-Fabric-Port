package net.tropicraft.core.common.dimension.carver;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.tropicraft.Constants;

public final class TropicraftConfiguredCarvers {
    public static ConfiguredWorldCarver<?> cave;
    public static ConfiguredWorldCarver<?> canyon;
    public static ConfiguredWorldCarver<?> underwaterCave;
    public static ConfiguredWorldCarver<?> underwaterCanyon;

    public static void configuredCarversRegister() {


        cave = register("cave", TropicraftCarvers.CAVE, new ProbabilityFeatureConfiguration(0.25F));
        canyon = register("canyon", TropicraftCarvers.CANYON, new ProbabilityFeatureConfiguration(0.02F));
        underwaterCave = register("underwater_cave", TropicraftCarvers.UNDERWATER_CAVE, new ProbabilityFeatureConfiguration(0.15F));
        underwaterCanyon = register("underwater_canyon", TropicraftCarvers.UNDERWATER_CANYON, new ProbabilityFeatureConfiguration(0.02F));
    }

    public static void addLand(BiomeGenerationSettings.Builder generation) {
        generation.addCarver(GenerationStep.Carving.AIR, cave);
        generation.addCarver(GenerationStep.Carving.AIR, canyon);
    }

    public static void addUnderwater(BiomeGenerationSettings.Builder generation) {
        generation.addCarver(GenerationStep.Carving.LIQUID, underwaterCave);
        generation.addCarver(GenerationStep.Carving.LIQUID, underwaterCanyon);
    }


    public static <C extends CarverConfiguration, WC extends WorldCarver<C>> ConfiguredWorldCarver<?> register(String id, WC carver, C config) {
        //return this.worldgen.register(new Identifier(Constants.MODID, id), carver.configure(config));
        return Registry.register(BuiltinRegistries.CONFIGURED_CARVER, new ResourceLocation(Constants.MODID, id), carver.configured(config));
    }
}
