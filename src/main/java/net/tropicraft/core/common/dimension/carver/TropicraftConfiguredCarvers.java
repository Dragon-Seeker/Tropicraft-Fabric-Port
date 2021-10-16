package net.tropicraft.core.common.dimension.carver;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.tropicraft.Constants;

import java.util.Random;

public final class TropicraftConfiguredCarvers {
    public static ConfiguredWorldCarver<?> cave;
    public static ConfiguredWorldCarver<?> canyon;
    public static ConfiguredWorldCarver<?> underwaterCave;
    public static ConfiguredWorldCarver<?> underwaterCanyon;

    public static CaveCarverConfiguration tropicraftCaveCarverConfig = new CaveCarverConfiguration(0.25F,
            BiasedToBottomHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(248), 8),
            ConstantFloat.of(0.5F),
            VerticalAnchor.aboveBottom(10),
            false,
            CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()),
            ConstantFloat.of(1.0F),
            ConstantFloat.of(1.0F),
            ConstantFloat.of(-0.7F));

    public static CanyonCarverConfiguration tropicraftCanyonCarverConfig = new CanyonCarverConfiguration(0.02F,
            BiasedToBottomHeight.of(VerticalAnchor.absolute(20), VerticalAnchor.absolute(67), 8),
            ConstantFloat.of(3.0F),
            VerticalAnchor.aboveBottom(10),
            false,
            CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()),
            UniformFloat.of(-0.125F, 0.125F),
            new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.75F, 1.0F),
                    TrapezoidFloat.of(0.0F, 6.0F, 2.0F),
                    3,
                    UniformFloat.of(0.75F, 1.0F),
                    1.0F,
                    0.0F));

    public static CaveCarverConfiguration tropicraftUnderWaterCaveCarverConfig = new CaveCarverConfiguration(0.15F,
            BiasedToBottomHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(240), 8),
            ConstantFloat.of(0.5F),
            VerticalAnchor.aboveBottom(10),
            false,
            CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()),
            ConstantFloat.of(1.0F),
            ConstantFloat.of(1.0F),
            ConstantFloat.of(-0.7F));

    public static CanyonCarverConfiguration tropicUnderWaterCanyonCarverConfig = new CanyonCarverConfiguration(0.02F,
            BiasedToBottomHeight.of(VerticalAnchor.absolute(20), VerticalAnchor.absolute(67), 8),
            ConstantFloat.of(3.0F), VerticalAnchor.aboveBottom(10),
            false,
            CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()),
            UniformFloat.of(-0.125F, 0.125F),
            new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.75F, 1.0F),
                    TrapezoidFloat.of(0.0F, 6.0F, 2.0F),
                    3, UniformFloat.of(0.75F, 1.0F),
                    1.0F,
                    0.0F));

    public static void configuredCarversRegister() {


        cave = register("cave", TropicraftCarvers.CAVE, tropicraftCaveCarverConfig);
        canyon = register("canyon", TropicraftCarvers.CANYON, tropicraftCanyonCarverConfig);
        underwaterCave = register("underwater_cave", TropicraftCarvers.UNDERWATER_CAVE, tropicraftUnderWaterCaveCarverConfig);
        underwaterCanyon = register("underwater_canyon", TropicraftCarvers.UNDERWATER_CANYON, tropicUnderWaterCanyonCarverConfig);
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
