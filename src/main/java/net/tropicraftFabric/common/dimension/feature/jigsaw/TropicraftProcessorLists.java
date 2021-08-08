package net.tropicraftFabric.common.dimension.feature.jigsaw;

import com.google.common.collect.ImmutableList;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.client.data.WorldgenDataConsumer;
import net.tropicraftFabric.common.registry.TropicraftBlocks;

import java.util.Arrays;

public final class TropicraftProcessorLists {
    public final StructureProcessorList koaTownCenters;
    public final StructureProcessorList koaBuildings;

    public final StructureProcessorList homeTreeBase;
    public final StructureProcessorList homeTreeStart;

    public TropicraftProcessorLists(WorldgenDataConsumer<StructureProcessorList> worldgen) {
        Register processors = new Register(worldgen);

        StructureSupportsProcessor fenceExtender = new StructureSupportsProcessor(false, ImmutableList.of(Registry.BLOCK.getId(TropicraftBlocks.BAMBOO_FENCE)));

        this.koaTownCenters = processors.register(
                "koa_village/town_centers",
                fenceExtender,
                new StructureVoidProcessor()
        );

        this.koaBuildings = processors.register(
                "koa_village/buildings",
                new AdjustBuildingHeightProcessor(126),
                fenceExtender,
                new StructureVoidProcessor()
        );

        // TODO add SpawnerProcessor
        this.homeTreeBase = processors.register("home_tree/base", new AirToCaveAirProcessor());
        this.homeTreeStart = processors.register(
                "home_tree/start",
                new AirToCaveAirProcessor(),
                new StructureSupportsProcessor(true, ImmutableList.of(Registry.BLOCK.getId(TropicraftBlocks.MAHOGANY_LOG)))
        );
    }

    static final class Register {
        private final WorldgenDataConsumer<StructureProcessorList> worldgen;

        Register(WorldgenDataConsumer<StructureProcessorList> worldgen) {
            this.worldgen = worldgen;
        }

        public StructureProcessorList register(String id, StructureProcessor... processors) {
            return this.worldgen.register(new Identifier(Constants.MODID, id), new StructureProcessorList(Arrays.asList(processors)));
        }
    }
}
