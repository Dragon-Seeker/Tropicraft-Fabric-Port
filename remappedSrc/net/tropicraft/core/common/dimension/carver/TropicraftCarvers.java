package net.tropicraft.core.common.dimension.carver;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.tropicraft.Constants;

public class TropicraftCarvers {
    public static final TropicsCaveCarver CAVE = register("cave", new TropicsCaveCarver(ProbabilityFeatureConfiguration.CODEC));
    public static final TropicsCanyonCarver CANYON = register("canyon", new TropicsCanyonCarver(ProbabilityFeatureConfiguration.CODEC));

    public static final TropicsUnderwaterCaveCarver UNDERWATER_CAVE = register("underwater_cave", new TropicsUnderwaterCaveCarver(ProbabilityFeatureConfiguration.CODEC));
    public static final TropicsUnderwaterCanyonCarver UNDERWATER_CANYON = register("underwater_canyon", new TropicsUnderwaterCanyonCarver(ProbabilityFeatureConfiguration.CODEC));

    //TODO: Check if this worked or not as it may not
    private static <T extends WorldCarver> T register(String id, T carver){
        return (T) Registry.register(Registry.CARVER, new ResourceLocation(Constants.MODID, id), carver);
    }

    public static void init(){

    }

}

