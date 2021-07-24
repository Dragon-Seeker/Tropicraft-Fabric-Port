package com.dragonseeker.tropicfabricport.common.world.feature;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class tropicFeatures {



    public static void registerFeatures(){
        //register("basic_sapling_test", new tropic);
    }

    private static <T extends FeatureConfig> void register(String id, StructureFeature<T> structure) {
        FabricStructureBuilder.create(new Identifier(Tropicfabricport.MOD_ID, id), structure)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(10, 1, 0)
                .register();
    }
}
