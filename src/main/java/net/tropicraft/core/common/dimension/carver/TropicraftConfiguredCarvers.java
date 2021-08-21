package net.tropicraft.core.common.dimension.carver;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.tropicraft.Constants;

public final class TropicraftConfiguredCarvers {
    public static ConfiguredCarver<?> cave;
    public static ConfiguredCarver<?> canyon;
    public static ConfiguredCarver<?> underwaterCave;
    public static ConfiguredCarver<?> underwaterCanyon;

    public static void configuredCarversRegister() {


        cave = register("cave", TropicraftCarvers.CAVE, new ProbabilityConfig(0.25F));
        canyon = register("canyon", TropicraftCarvers.CANYON, new ProbabilityConfig(0.02F));
        underwaterCave = register("underwater_cave", TropicraftCarvers.UNDERWATER_CAVE, new ProbabilityConfig(0.15F));
        underwaterCanyon = register("underwater_canyon", TropicraftCarvers.UNDERWATER_CANYON, new ProbabilityConfig(0.02F));
    }

    public static void addLand(GenerationSettings.Builder generation) {
        generation.carver(GenerationStep.Carver.AIR, cave);
        generation.carver(GenerationStep.Carver.AIR, canyon);
    }

    public static void addUnderwater(GenerationSettings.Builder generation) {
        generation.carver(GenerationStep.Carver.LIQUID, underwaterCave);
        generation.carver(GenerationStep.Carver.LIQUID, underwaterCanyon);
    }


    public static <C extends CarverConfig, WC extends Carver<C>> ConfiguredCarver<?> register(String id, WC carver, C config) {
        //return this.worldgen.register(new Identifier(Constants.MODID, id), carver.configure(config));
        return Registry.register(BuiltinRegistries.CONFIGURED_CARVER, new Identifier(Constants.MODID, id), carver.configure(config));
    }
}
