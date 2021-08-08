package net.tropicraftFabric.common.dimension.feature.pools;

import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.client.data.WorldgenDataConsumer;
import net.tropicraftFabric.common.dimension.feature.TropicraftConfiguredFeatures;
import net.tropicraftFabric.common.dimension.feature.TropicraftFeatures;
import net.tropicraftFabric.common.dimension.feature.jigsaw.TropicraftProcessorLists;
import net.tropicraftFabric.common.dimension.feature.jigsaw.piece.NoRotateSingleJigsawPiece;
import net.tropicraftFabric.common.dimension.feature.jigsaw.piece.SingleNoAirJigsawPiece;

import java.util.Arrays;
import java.util.function.Function;

public final class TropicraftTemplatePools {
    public final StructurePool koaTownCenters;
    public final StructurePool koaHuts;
    public final StructurePool koaStreets;
    public final StructurePool koaTerminators;
    public final StructurePool koaVillagers;
    public final StructurePool koaFish;
    public final StructurePool homeTreeStarts;
    public final StructurePool homeTreeRoofs;
    public final StructurePool homeTreeDummy;
    public final StructurePool homeTreeTrunkMiddle;
    public final StructurePool homeTreeTrunkTop;
    public final StructurePool homeTreeBranchesSouth;
    public final StructurePool homeTreeBranchesSouthEast;
    public final StructurePool homeTreeBranchesEast;
    public final StructurePool homeTreeBranchesNorthEast;
    public final StructurePool homeTreeBranchesNorth;
    public final StructurePool homeTreeBranchesNorthWest;
    public final StructurePool homeTreeBranchesWest;
    public final StructurePool homeTreeBranchesSouthWest;

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

    private static Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> singlePiece(String path, StructureProcessorList processors, int weight) {
        return new Pair<>(
                StructurePoolElement.method_30435(Constants.MODID + ":" + path, processors),
                weight
        );
    }

    private static Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> singlePiece(String path, int weight) {
        return new Pair<>(
                StructurePoolElement.method_30434(Constants.MODID + ":" + path),
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
                StructurePoolElement.method_30421(feature),
                weight
        );
    }

    static final class Register {
        private final WorldgenDataConsumer<StructurePool> worldgen;

        Register(WorldgenDataConsumer<StructurePool> worldgen) {
            this.worldgen = worldgen;
        }

        @SafeVarargs
        public final StructurePool register(String id, StructurePool.Projection placementBehaviour, Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>... pieces) {
            return this.register(id, new Identifier("empty"), placementBehaviour, pieces);
        }

        @SafeVarargs
        public final StructurePool register(String id, Identifier fallback, StructurePool.Projection placementBehaviour, Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>... pieces) {
            return this.register(new StructurePool(new Identifier(Constants.MODID, id), fallback, Arrays.asList(pieces), placementBehaviour));
        }

        public StructurePool register(StructurePool templatePool) {
            return this.worldgen.register(templatePool.getId(), templatePool);
        }
    }
}
