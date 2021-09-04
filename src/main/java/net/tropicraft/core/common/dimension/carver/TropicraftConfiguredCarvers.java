package net.tropicraft.core.common.dimension.carver;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.TrapezoidFloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.heightprovider.BiasedToBottomHeightProvider;
import net.tropicraft.Constants;

import java.util.Random;

public final class TropicraftConfiguredCarvers {
    public static ConfiguredCarver<?> cave;
    public static ConfiguredCarver<?> canyon;
    public static ConfiguredCarver<?> underwaterCave;
    public static ConfiguredCarver<?> underwaterCanyon;

    public static CaveCarverConfig tropicraftCaveCarverConfig = new CaveCarverConfig(0.25F,
            BiasedToBottomHeightProvider.create(YOffset.fixed(0), YOffset.fixed(248), 8),
            ConstantFloatProvider.create(0.5F),
            YOffset.aboveBottom(10),
            false,
            CarverDebugConfig.create(false, Blocks.CRIMSON_BUTTON.getDefaultState()),
            ConstantFloatProvider.create(1.0F),
            ConstantFloatProvider.create(1.0F),
            ConstantFloatProvider.create(-0.7F));

    public static RavineCarverConfig tropicraftCanyonCarverConfig = new RavineCarverConfig(0.02F,
            BiasedToBottomHeightProvider.create(YOffset.fixed(20), YOffset.fixed(67), 8),
            ConstantFloatProvider.create(3.0F),
            YOffset.aboveBottom(10),
            false,
            CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.getDefaultState()),
            UniformFloatProvider.create(-0.125F, 0.125F),
            new RavineCarverConfig.Shape(UniformFloatProvider.create(0.75F, 1.0F),
                    TrapezoidFloatProvider.create(0.0F, 6.0F, 2.0F),
                    3,
                    UniformFloatProvider.create(0.75F, 1.0F),
                    1.0F,
                    0.0F));

    public static CaveCarverConfig tropicraftUnderWaterCaveCarverConfig = new CaveCarverConfig(0.15F,
            BiasedToBottomHeightProvider.create(YOffset.fixed(0), YOffset.fixed(240), 8),
            ConstantFloatProvider.create(0.5F),
            YOffset.aboveBottom(10),
            false,
            CarverDebugConfig.create(false, Blocks.CRIMSON_BUTTON.getDefaultState()),
            ConstantFloatProvider.create(1.0F),
            ConstantFloatProvider.create(1.0F),
            ConstantFloatProvider.create(-0.7F));

    public static RavineCarverConfig tropicUnderWaterCanyonCarverConfig = new RavineCarverConfig(0.02F,
            BiasedToBottomHeightProvider.create(YOffset.fixed(20), YOffset.fixed(67), 8),
            ConstantFloatProvider.create(3.0F), YOffset.aboveBottom(10),
            false,
            CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.getDefaultState()),
            UniformFloatProvider.create(-0.125F, 0.125F),
            new RavineCarverConfig.Shape(UniformFloatProvider.create(0.75F, 1.0F),
                    TrapezoidFloatProvider.create(0.0F, 6.0F, 2.0F),
                    3, UniformFloatProvider.create(0.75F, 1.0F),
                    1.0F,
                    0.0F));

    public static void configuredCarversRegister() {


        cave = register("cave", TropicraftCarvers.CAVE, tropicraftCaveCarverConfig);
        canyon = register("canyon", TropicraftCarvers.CANYON, tropicraftCanyonCarverConfig);
        underwaterCave = register("underwater_cave", TropicraftCarvers.UNDERWATER_CAVE, tropicraftUnderWaterCaveCarverConfig);
        underwaterCanyon = register("underwater_canyon", TropicraftCarvers.UNDERWATER_CANYON, tropicUnderWaterCanyonCarverConfig);
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
