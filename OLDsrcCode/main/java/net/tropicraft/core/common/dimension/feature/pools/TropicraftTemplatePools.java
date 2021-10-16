package net.tropicraft.core.common.dimension.feature.pools;

import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.tropicraft.Constants;
import net.tropicraft.core.common.dimension.feature.TropicraftConfiguredFeatures;
import net.tropicraft.core.common.dimension.feature.TropicraftFeatures;
import net.tropicraft.core.common.dimension.feature.jigsaw.TropicraftProcessorLists;
import net.tropicraft.core.common.dimension.feature.jigsaw.piece.NoRotateSingleJigsawPiece;
import net.tropicraft.core.common.dimension.feature.jigsaw.piece.SingleNoAirJigsawPiece;

import java.util.Arrays;
import java.util.function.Function;

public final class TropicraftTemplatePools {
    public static StructurePool koaTownCenters;
    public static StructurePool koaHuts;
    public static StructurePool koaStreets;
    public static StructurePool koaTerminators;
    public static StructurePool koaVillagers;
    public static StructurePool koaFish;
    public static StructurePool homeTreeStarts;
    public static StructurePool homeTreeRoofs;
    public static StructurePool homeTreeDummy;
    public static StructurePool homeTreeTrunkMiddle;
    public static StructurePool homeTreeTrunkTop;
    public static StructurePool homeTreeBranchesSouth;
    public static StructurePool homeTreeBranchesSouthEast;
    public static StructurePool homeTreeBranchesEast;
    public static StructurePool homeTreeBranchesNorthEast;
    public static StructurePool homeTreeBranchesNorth;
    public static StructurePool homeTreeBranchesNorthWest;
    public static StructurePool homeTreeBranchesWest;
    public static StructurePool homeTreeBranchesSouthWest;

    /*
    public TropicraftTemplatePools(WorldgenDataConsumer<StructurePool> worldgen, TropicraftConfiguredFeatures features, TropicraftProcessorLists processors) {
        Register pools = new Register(worldgen);

        this.koaTownCenters = pools.register(
                "koa_village/town_centers",
                StructurePool.Projection.RIGID,
                noAirSingle("koa_village/town_centers/firepit_01", processors.koaTownCenters, 1)
        );

        this.koaHuts = pools.register(
                "koa_village/huts",
                StructurePool.Projection.RIGID,
                noAirSingle("koa_village/huts/hut_01", processors.koaBuildings, 5),
                noAirSingle("koa_village/huts/hut_02", processors.koaBuildings, 2),
                noAirSingle("koa_village/huts/hut_03", processors.koaBuildings, 3),
                noAirSingle("koa_village/huts/hut_04", processors.koaBuildings, 4),
                noAirSingle("koa_village/huts/hut_05", processors.koaBuildings, 10),
                noAirSingle("koa_village/huts/bongo_hut_01", processors.koaBuildings, 2),
                noAirSingle("koa_village/huts/trade_hut_01", processors.koaBuildings, 2)
        );

        this.koaStreets = pools.register(
                "koa_village/streets",
                new Identifier(Constants.MODID, "koa_village/terminators"),
                TropicraftFeatures.KOA_PATH,
                noAirSingle("koa_village/streets/straight_01", 3),
                noAirSingle("koa_village/streets/straight_02", 4),
                noAirSingle("koa_village/streets/straight_03", 10),
                noAirSingle("koa_village/streets/straight_04", 2),
                noAirSingle("koa_village/streets/straight_05", 3),
                noAirSingle("koa_village/streets/straight_06", 2),
                noAirSingle("koa_village/streets/corner_01", 2),
                noAirSingle("koa_village/streets/corner_02", 4),
                noAirSingle("koa_village/streets/corner_03", 6),
                noAirSingle("koa_village/streets/corner_04", 2),
                noAirSingle("koa_village/streets/crossroad_01", 5),
                noAirSingle("koa_village/streets/crossroad_02", 2),
                noAirSingle("koa_village/streets/crossroad_03", 1),
                noAirSingle("koa_village/streets/crossroad_04", 2)
        );

        this.koaTerminators = pools.register(
                "koa_village/terminators",
                TropicraftFeatures.KOA_PATH,
                noAirSingle("koa_village/terminators/terminator_01", 1)
        );

        this.koaVillagers = pools.register(
                "koa_village/villagers",
                StructurePool.Projection.RIGID,
                noAirSingle("koa_village/villagers/unemployed", 1)
        );

        this.koaFish = pools.register(
                "koa_village/fish",
                StructurePool.Projection.RIGID,
                noAirSingle("koa_village/fish/fish_01", 1)
        );

        this.homeTreeStarts = pools.register(
                "home_tree/starts",
                StructurePool.Projection.RIGID,
                singlePiece("home_tree/trunks/bottom/trunk_0", processors.homeTreeStart, 1),
                singlePiece("home_tree/trunks/bottom/trunk_1", processors.homeTreeStart, 1)
        );

        this.homeTreeRoofs = pools.register(
                "home_tree/roofs",
                StructurePool.Projection.RIGID,
                singlePiece("home_tree/roofs/roof_0", processors.homeTreeBase, 1)
        );

        this.homeTreeDummy = pools.register(
                "home_tree/dummy",
                StructurePool.Projection.RIGID,
                singlePiece("home_tree/dummy", processors.homeTreeBase, 1),
                singlePiece("home_tree/outer_dummy", processors.homeTreeBase, 1)
        );

        this.homeTreeTrunkMiddle = pools.register(
                "home_tree/trunks/middle",
                StructurePool.Projection.RIGID,
                singlePiece("home_tree/trunks/middle/trunk_0", processors.homeTreeBase, 1),
                singlePiece("home_tree/trunks/middle/trunk_1", processors.homeTreeBase, 1),
                singlePiece("home_tree/trunks/middle/trunk_1_iguanas", processors.homeTreeBase, 1),
                singlePiece("home_tree/trunks/middle/trunk_1_ashen", processors.homeTreeBase, 1)
        );

        this.homeTreeTrunkTop = pools.register(
                "home_tree/trunks/top",
                StructurePool.Projection.RIGID,
                noRotateSingle("home_tree/trunks/top/trunk_0", processors.homeTreeBase, 1),
                noRotateSingle("home_tree/trunks/top/trunk_1", processors.homeTreeBase, 1),
                noRotateSingle("home_tree/trunks/top/trunk_2", processors.homeTreeBase, 1),
                noRotateSingle("home_tree/trunks/top/trunk_3", processors.homeTreeBase, 1)
        );

        this.homeTreeBranchesSouth = pools.register(
                "home_tree/branches/south",
                StructurePool.Projection.RIGID,
                feature(features.homeTreeBranchSouth, 4),
                feature(features.homeTreeBranchSouthExact, 1)
        );
        this.homeTreeBranchesSouthEast = pools.register(
                "home_tree/branches/southeast",
                StructurePool.Projection.RIGID,
                feature(features.homeTreeBranchSouthEast, 4),
                feature(features.homeTreeBranchSouthEastExact, 1)
        );
        this.homeTreeBranchesEast = pools.register(
                "home_tree/branches/east",
                StructurePool.Projection.RIGID,
                feature(features.homeTreeBranchEast, 4),
                feature(features.homeTreeBranchEastExact, 1)
        );
        this.homeTreeBranchesNorthEast = pools.register(
                "home_tree/branches/northeast",
                StructurePool.Projection.RIGID,
                feature(features.homeTreeBranchNorthEast, 4),
                feature(features.homeTreeBranchNorthEastExact, 1)
        );
        this.homeTreeBranchesNorth = pools.register(
                "home_tree/branches/north",
                StructurePool.Projection.RIGID,
                feature(features.homeTreeBranchNorth, 4),
                feature(features.homeTreeBranchNorthExact, 1)
        );
        this.homeTreeBranchesNorthWest = pools.register(
                "home_tree/branches/northwest",
                StructurePool.Projection.RIGID,
                feature(features.homeTreeBranchNorthWest, 4),
                feature(features.homeTreeBranchNorthWestExact, 1)
        );
        this.homeTreeBranchesWest = pools.register(
                "home_tree/branches/west",
                StructurePool.Projection.RIGID,
                feature(features.homeTreeBranchWest, 4),
                feature(features.homeTreeBranchWestExact, 1)
        );
        this.homeTreeBranchesSouthWest = pools.register(
                "home_tree/branches/southwest",
                StructurePool.Projection.RIGID,
                feature(features.homeTreeBranchSouthWest, 4),
                feature(features.homeTreeBranchSouthWestExact, 1)
        );
    }
     */

    public static void structurePoolRegistery() {
        //Register pools = new Register(worldgen);

        koaTownCenters = register(
                "koa_village/town_centers",
                StructurePool.Projection.RIGID,
                noAirSingle("koa_village/town_centers/firepit_01", TropicraftProcessorLists.koaTownCenters, 1)
        );

        koaHuts = register(
                "koa_village/huts",
                StructurePool.Projection.RIGID,
                noAirSingle("koa_village/huts/hut_01", TropicraftProcessorLists.koaBuildings, 5),
                noAirSingle("koa_village/huts/hut_02", TropicraftProcessorLists.koaBuildings, 2),
                noAirSingle("koa_village/huts/hut_03", TropicraftProcessorLists.koaBuildings, 3),
                noAirSingle("koa_village/huts/hut_04", TropicraftProcessorLists.koaBuildings, 4),
                noAirSingle("koa_village/huts/hut_05", TropicraftProcessorLists.koaBuildings, 10),
                noAirSingle("koa_village/huts/bongo_hut_01", TropicraftProcessorLists.koaBuildings, 2),
                noAirSingle("koa_village/huts/trade_hut_01", TropicraftProcessorLists.koaBuildings, 2)
        );

        //TODO: Fix Koa Path no existing or something
        koaStreets = register(
                "koa_village/streets",
                new Identifier(Constants.MODID, "koa_village/terminators"),
                TropicraftFeatures.KOA_PATH,
                noAirSingle("koa_village/streets/straight_01", 3),
                noAirSingle("koa_village/streets/straight_02", 4),
                noAirSingle("koa_village/streets/straight_03", 10),
                noAirSingle("koa_village/streets/straight_04", 2),
                noAirSingle("koa_village/streets/straight_05", 3),
                noAirSingle("koa_village/streets/straight_06", 2),
                noAirSingle("koa_village/streets/corner_01", 2),
                noAirSingle("koa_village/streets/corner_02", 4),
                noAirSingle("koa_village/streets/corner_03", 6),
                noAirSingle("koa_village/streets/corner_04", 2),
                noAirSingle("koa_village/streets/crossroad_01", 5),
                noAirSingle("koa_village/streets/crossroad_02", 2),
                noAirSingle("koa_village/streets/crossroad_03", 1),
                noAirSingle("koa_village/streets/crossroad_04", 2)
        );

        koaTerminators = register(
                "koa_village/terminators",
                TropicraftFeatures.KOA_PATH,
                noAirSingle("koa_village/terminators/terminator_01", 1)
        );


        koaVillagers = register(
                "koa_village/villagers",
                StructurePool.Projection.RIGID,
                noAirSingle("koa_village/villagers/unemployed", 1)
        );

        koaFish = register(
                "koa_village/fish",
                StructurePool.Projection.RIGID,
                noAirSingle("koa_village/fish/fish_01", 1)
        );

        homeTreeStarts = register(
                "home_tree/starts",
                StructurePool.Projection.RIGID,
                singlePiece("home_tree/trunks/bottom/trunk_0", TropicraftProcessorLists.homeTreeStart, 1),
                singlePiece("home_tree/trunks/bottom/trunk_1", TropicraftProcessorLists.homeTreeStart, 1)
        );

        homeTreeRoofs = register(
                "home_tree/roofs",
                StructurePool.Projection.RIGID,
                singlePiece("home_tree/roofs/roof_0", TropicraftProcessorLists.homeTreeBase, 1)
        );

        homeTreeDummy = register(
                "home_tree/dummy",
                StructurePool.Projection.RIGID,
                singlePiece("home_tree/dummy", TropicraftProcessorLists.homeTreeBase, 1),
                singlePiece("home_tree/outer_dummy", TropicraftProcessorLists.homeTreeBase, 1)
        );

        homeTreeTrunkMiddle = register(
                "home_tree/trunks/middle",
                StructurePool.Projection.RIGID,
                singlePiece("home_tree/trunks/middle/trunk_0", TropicraftProcessorLists.homeTreeBase, 1),
                singlePiece("home_tree/trunks/middle/trunk_1", TropicraftProcessorLists.homeTreeBase, 1),
                singlePiece("home_tree/trunks/middle/trunk_1_iguanas", TropicraftProcessorLists.homeTreeBase, 1),
                singlePiece("home_tree/trunks/middle/trunk_1_ashen", TropicraftProcessorLists.homeTreeBase, 1)
        );

        homeTreeTrunkTop = register(
                "home_tree/trunks/top",
                StructurePool.Projection.RIGID,
                noRotateSingle("home_tree/trunks/top/trunk_0", TropicraftProcessorLists.homeTreeBase, 1),
                noRotateSingle("home_tree/trunks/top/trunk_1", TropicraftProcessorLists.homeTreeBase, 1),
                noRotateSingle("home_tree/trunks/top/trunk_2", TropicraftProcessorLists.homeTreeBase, 1),
                noRotateSingle("home_tree/trunks/top/trunk_3", TropicraftProcessorLists.homeTreeBase, 1)
        );

        homeTreeBranchesSouth = register(
                "home_tree/branches/south",
                StructurePool.Projection.RIGID,
                feature(TropicraftConfiguredFeatures.homeTreeBranchSouth, 4),
                feature(TropicraftConfiguredFeatures.homeTreeBranchSouthExact, 1)
        );
        homeTreeBranchesSouthEast = register(
                "home_tree/branches/southeast",
                StructurePool.Projection.RIGID,
                feature(TropicraftConfiguredFeatures.homeTreeBranchSouthEast, 4),
                feature(TropicraftConfiguredFeatures.homeTreeBranchSouthEastExact, 1)
        );
        homeTreeBranchesEast = register(
                "home_tree/branches/east",
                StructurePool.Projection.RIGID,
                feature(TropicraftConfiguredFeatures.homeTreeBranchEast, 4),
                feature(TropicraftConfiguredFeatures.homeTreeBranchEastExact, 1)
        );
        homeTreeBranchesNorthEast = register(
                "home_tree/branches/northeast",
                StructurePool.Projection.RIGID,
                feature(TropicraftConfiguredFeatures.homeTreeBranchNorthEast, 4),
                feature(TropicraftConfiguredFeatures.homeTreeBranchNorthEastExact, 1)
        );
        homeTreeBranchesNorth = register(
                "home_tree/branches/north",
                StructurePool.Projection.RIGID,
                feature(TropicraftConfiguredFeatures.homeTreeBranchNorth, 4),
                feature(TropicraftConfiguredFeatures.homeTreeBranchNorthExact, 1)
        );
        homeTreeBranchesNorthWest = register(
                "home_tree/branches/northwest",
                StructurePool.Projection.RIGID,
                feature(TropicraftConfiguredFeatures.homeTreeBranchNorthWest, 4),
                feature(TropicraftConfiguredFeatures.homeTreeBranchNorthWestExact, 1)
        );
        homeTreeBranchesWest = register(
                "home_tree/branches/west",
                StructurePool.Projection.RIGID,
                feature(TropicraftConfiguredFeatures.homeTreeBranchWest, 4),
                feature(TropicraftConfiguredFeatures.homeTreeBranchWestExact, 1)
        );
        homeTreeBranchesSouthWest = register(
                "home_tree/branches/southwest",
                StructurePool.Projection.RIGID,
                feature(TropicraftConfiguredFeatures.homeTreeBranchSouthWest, 4),
                feature(TropicraftConfiguredFeatures.homeTreeBranchSouthWestExact, 1)
        );
    }

    private static Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> singlePiece(String path, StructureProcessorList processors, int weight) {
        return new Pair<>(
                StructurePoolElement.ofProcessedSingle(Constants.MODID + ":" + path, processors),
                weight
        );
    }

    private static Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> singlePiece(String path, int weight) {
        return new Pair<>(
                StructurePoolElement.ofSingle(Constants.MODID + ":" + path),
                weight
        );
    }

    private static Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> noAirSingle(String path, StructureProcessorList processors, int weight) {
        return new Pair<>(
                SingleNoAirJigsawPiece.create(Constants.MODID + ":" + path, processors),
                weight
        );
    }

    private static Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> noAirSingle(String path, int weight) {
        return new Pair<>(
                SingleNoAirJigsawPiece.create(Constants.MODID + ":" + path),
                weight
        );
    }

    private static Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> noRotateSingle(String path, StructureProcessorList processors, int weight) {
        return new Pair<>(
                NoRotateSingleJigsawPiece.createNoRotate(Constants.MODID + ":" + path, processors),
                weight
        );
    }

    private static Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> feature(ConfiguredFeature<?, ?> feature, int weight) {
        return new Pair<>(
                StructurePoolElement.ofFeature(feature),
                weight
        );
    }



    @SafeVarargs
    public static final StructurePool register(String id, StructurePool.Projection placementBehaviour, Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>... pieces) {
        return register(id, new Identifier("empty"), placementBehaviour, pieces);
    }

    @SafeVarargs
    public static final StructurePool register(String id, Identifier fallback, StructurePool.Projection placementBehaviour, Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>... pieces) {
        return register(new StructurePool(new Identifier(Constants.MODID, id), fallback, Arrays.asList(pieces), placementBehaviour));
    }

    public static StructurePool register(StructurePool templatePool) {
        //return this.worldgen.register(templatePool.getId(), templatePool);
        return (StructurePool)BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_POOL, templatePool.getId(), templatePool);
        //return Registry.register(BuiltinRegistries.STRUCTURE_POOL, templatePool.getId(), templatePool);
    }

}
