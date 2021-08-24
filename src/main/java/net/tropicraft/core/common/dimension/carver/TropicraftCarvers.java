package net.tropicraft.core.common.dimension.carver;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.RavineCarverConfig;
import net.tropicraft.Constants;

public class TropicraftCarvers {
    public static final TropicsCaveCarver CAVE = register("cave", new TropicsCaveCarver(ProbabilityConfig.CODEC));
    public static final TropicsCanyonCarver CANYON = register("canyon", new TropicsCanyonCarver(ProbabilityConfig.CODEC));

    public static final TropicsUnderwaterCaveCarver UNDERWATER_CAVE = register("underwater_cave", new TropicsUnderwaterCaveCarver(ProbabilityConfig.CODEC));
    public static final TropicsUnderwaterCanyonCarver UNDERWATER_CANYON = register("underwater_canyon", new TropicsUnderwaterCanyonCarver(RavineCarverConfig.CODEC));

    //TODO: Check if this worked or not as it may not
    private static <T extends Carver> T register(String id, T carver){
        return (T) Registry.register(Registry.CARVER, new Identifier(Constants.MODID, id), carver);
    }

    public static void init(){

    }

}

