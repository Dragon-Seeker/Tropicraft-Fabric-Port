package net.tropicraft.core.common.dimension.feature.jigsaw;

import com.google.common.collect.ImmutableList;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.Arrays;

public final class TropicraftProcessorLists {
    public static StructureProcessorList koaTownCenters;
    public static StructureProcessorList koaBuildings;

    public static StructureProcessorList homeTreeBase;
    public static StructureProcessorList homeTreeStart;

    public static void processorListsRegister() {
        StructureSupportsProcessor fenceExtender = new StructureSupportsProcessor(false, ImmutableList.of(Registry.BLOCK.getId(TropicraftBlocks.BAMBOO_FENCE)));

        koaTownCenters = register(
                "koa_village/town_centers",
                fenceExtender,
                new StructureVoidProcessor()
        );

        koaBuildings = register(
                "koa_village/buildings",
                new AdjustBuildingHeightProcessor(126),
                fenceExtender,
                new StructureVoidProcessor()
        );

        // TODO add SpawnerProcessor
        homeTreeBase = register("home_tree/base", new AirToCaveAirProcessor());
        homeTreeStart = register(
                "home_tree/start",
                new AirToCaveAirProcessor(),
                new StructureSupportsProcessor(true, ImmutableList.of(Registry.BLOCK.getId(TropicraftBlocks.MAHOGANY_LOG)))
        );
    }


    public static StructureProcessorList register(String id, StructureProcessor... processors) {
        //return this.worldgen.register(new Identifier(Constants.MODID, id), new StructureProcessorList(Arrays.asList(processors)));
        return Registry.register(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, new Identifier(Constants.MODID, id), new StructureProcessorList(Arrays.asList(processors)));
    }

}
