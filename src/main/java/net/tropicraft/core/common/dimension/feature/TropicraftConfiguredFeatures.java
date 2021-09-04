package net.tropicraft.core.common.dimension.feature;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.loom.configuration.FabricApiExtension;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CarvingMaskDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.tropicraft.Constants;
//import net.tropicraftFabric.common.dimension.feature.block_state_provider.NoiseFromTagBlockStateProvider;
import net.tropicraft.core.common.dimension.feature.block_state_provider.NoiseFromTagRainForestFlowerBlockStateProvider;
import net.tropicraft.core.common.dimension.feature.block_state_provider.NoiseFromTagTropicFlowerBlockStateProvider;
import net.tropicraft.core.common.dimension.feature.config.FruitTreeConfig;
import net.tropicraft.core.common.dimension.feature.config.HomeTreeBranchConfig;
import net.tropicraft.core.common.dimension.feature.config.RainforestVinesConfig;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class TropicraftConfiguredFeatures extends ConfiguredFeatures{
    //private static ConfiguredFeature<?,?> grapefruitTree;
    public static ConfiguredFeature<?, ?> grapefruitTree;
    public static ConfiguredFeature<?, ?> orangeTree;
    public static ConfiguredFeature<?, ?> lemonTree;
    public static ConfiguredFeature<?, ?> limeTree;
    public static ConfiguredFeature<?, ?> normalPalmTree;
    public static ConfiguredFeature<?, ?> curvedPalmTree;
    public static ConfiguredFeature<?, ?> largePalmTree;
    public static ConfiguredFeature<?, ?> rainforestUpTree;
    public static ConfiguredFeature<?, ?> rainforestSmallTualung;
    public static ConfiguredFeature<?, ?> rainforestLargeTualung;
    public static ConfiguredFeature<?, ?> rainforestTallTree;
    public static ConfiguredFeature<?, ?> rainforestVines;
    public static ConfiguredFeature<?, ?> eih;

    public static ConfiguredFeature<?, ?> pineapplePatch;
    public static ConfiguredFeature<?, ?> tropicsFlowers;
    public static ConfiguredFeature<?, ?> rainforestFlowers;
    public static ConfiguredFeature<?, ?> irisFlowers;

    public static ConfiguredFeature<?, ?> coffeeBush;
    public static ConfiguredFeature<?, ?> undergrowth;

    public static ConfiguredFeature<?, ?> undergroundSeagrassOnStone;
    public static ConfiguredFeature<?, ?> undergroundSeagrassOnDirt;
    public static ConfiguredFeature<?, ?> undergroundSeaPickles;

    public static ConfiguredFeature<?, ?> azurite;
    public static ConfiguredFeature<?, ?> eudialyte;
    public static ConfiguredFeature<?, ?> zircon;
    public static ConfiguredFeature<?, ?> manganese;
    public static ConfiguredFeature<?, ?> shaka;

    public static ConfiguredFeature<?, ?> homeTreeBranchSouth;
    public static ConfiguredFeature<?, ?> homeTreeBranchSouthExact;
    public static ConfiguredFeature<?, ?> homeTreeBranchSouthEast;
    public static ConfiguredFeature<?, ?> homeTreeBranchSouthEastExact;
    public static ConfiguredFeature<?, ?> homeTreeBranchEast;
    public static ConfiguredFeature<?, ?> homeTreeBranchEastExact;
    public static ConfiguredFeature<?, ?> homeTreeBranchNorthEast;
    public static ConfiguredFeature<?, ?> homeTreeBranchNorthEastExact;
    public static ConfiguredFeature<?, ?> homeTreeBranchNorth;
    public static ConfiguredFeature<?, ?> homeTreeBranchNorthExact;
    public static ConfiguredFeature<?, ?> homeTreeBranchNorthWest;
    public static ConfiguredFeature<?, ?> homeTreeBranchNorthWestExact;
    public static ConfiguredFeature<?, ?> homeTreeBranchWest;
    public static ConfiguredFeature<?, ?> homeTreeBranchWestExact;
    public static ConfiguredFeature<?, ?> homeTreeBranchSouthWest;
    public static ConfiguredFeature<?, ?> homeTreeBranchSouthWestExact;


    /*
    public TropicraftConfiguredFeatures(WorldgenDataConsumer<ConfiguredFeature<?, ?>> worldgen) {
        Register features = new Register(worldgen);

        this.grapefruitTree = features.fruitTree("grapefruit_tree", TropicraftBlocks.GRAPEFRUIT_SAPLING, TropicraftBlocks.GRAPEFRUIT_LEAVES);
        this.orangeTree = features.fruitTree("orange_tree", TropicraftBlocks.ORANGE_SAPLING, TropicraftBlocks.ORANGE_LEAVES);
        this.lemonTree = features.fruitTree("lemon_tree", TropicraftBlocks.LEMON_SAPLING, TropicraftBlocks.LEMON_LEAVES);
        this.limeTree = features.fruitTree("lime_tree", TropicraftBlocks.LIME_SAPLING, TropicraftBlocks.LIME_LEAVES);

        this.normalPalmTree = features.sparseTree("normal_palm_tree", TropicraftFeatures.NORMAL_PALM_TREE, FeatureConfig.DEFAULT, 0.2F);
        this.curvedPalmTree = features.sparseTree("curved_palm_tree", TropicraftFeatures.CURVED_PALM_TREE, FeatureConfig.DEFAULT, 0.2F);
        this.largePalmTree = features.sparseTree("large_palm_tree", TropicraftFeatures.LARGE_PALM_TREE, FeatureConfig.DEFAULT, 0.2F);

        this.rainforestUpTree = features.sparseTree("rainforest_up_tree", TropicraftFeatures.UP_TREE, FeatureConfig.DEFAULT, 0.2F);
        this.rainforestSmallTualung = features.sparseTree("rainforest_small_tualung", TropicraftFeatures.SMALL_TUALUNG, FeatureConfig.DEFAULT, 0.3F);
        this.rainforestLargeTualung = features.sparseTree("rainforest_large_tualung", TropicraftFeatures.LARGE_TUALUNG, FeatureConfig.DEFAULT, 0.4F);
        this.rainforestTallTree = features.sparseTree("rainforest_tall_tree", TropicraftFeatures.TALL_TREE, FeatureConfig.DEFAULT, 0.5F);
        this.rainforestVines = features.register("rainforest_vines", TropicraftFeatures.VINES,
                f -> f.configure(new RainforestVinesConfig()).spreadHorizontally().repeat(50)
        );

        this.eih = features.noConfig("eih", TropicraftFeatures.EIH,
                f -> f.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                        .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.01F, 1)))
        );

        this.pineapplePatch = features.register("pineapple_patch", Feature.RANDOM_PATCH, feature -> {
            SimpleBlockStateProvider state = new SimpleBlockStateProvider(TropicraftBlocks.PINEAPPLE.getDefaultState());
            return feature.configure(new RandomPatchFeatureConfig.Builder(state, new DoublePlantPlacer())
                    .tries(64)
                    .cannotProject()
                    .build()
            ).decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP);
        });
        this.tropicsFlowers = features.register("tropics_flowers", Feature.FLOWER, feature -> {
            BlockStateProvider stateProvider = new NoiseFromTagBlockStateProvider(TropicraftTags.Blocks.TROPICS_FLOWERS);
            RandomPatchFeatureConfig config = new RandomPatchFeatureConfig.Builder(stateProvider, SimpleBlockPlacer.INSTANCE).tries(64).build();
            return feature.configure(config).decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(12));
        });
        this.rainforestFlowers = features.register("rainforest_flowers", Feature.FLOWER, feature -> {
            BlockStateProvider stateProvider = new NoiseFromTagBlockStateProvider(TropicraftTags.Blocks.RAINFOREST_FLOWERS);
            RandomPatchFeatureConfig config = new RandomPatchFeatureConfig.Builder(stateProvider, SimpleBlockPlacer.INSTANCE).tries(64).cannotProject().build();
            return feature.configure(config).decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(4));
        });
        this.irisFlowers = features.register("iris_flowers", Feature.RANDOM_PATCH, feature -> {
            BlockStateProvider stateProvider = new SimpleBlockStateProvider(TropicraftBlocks.IRIS.getDefaultState());
            RandomPatchFeatureConfig config = new RandomPatchFeatureConfig.Builder(stateProvider, new DoublePlantPlacer()).tries(64).cannotProject().build();
            return feature.configure(config).decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(10));
        });

        this.coffeeBush = features.noConfig("coffee_bush", TropicraftFeatures.COFFEE_BUSH, feature -> {
            return feature.decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(5));
        });
        this.undergrowth = features.noConfig("undergrowth", TropicraftFeatures.UNDERGROWTH, feature -> {
            return feature.decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(100));
        });

        this.undergroundSeagrassOnStone = features.register("underground_seagrass_on_stone", Feature.SIMPLE_BLOCK, feature -> {
            SimpleBlockFeatureConfig config = new SimpleBlockFeatureConfig(
                    Blocks.SEAGRASS.getDefaultState(),
                    ImmutableList.of(Blocks.STONE.getDefaultState()),
                    ImmutableList.of(Blocks.WATER.getDefaultState()),
                    ImmutableList.of(Blocks.WATER.getDefaultState())
            );
            return feature.configure(config).decorate(Decorator.CARVING_MASK.configure(new CarvingMaskDecoratorConfig(GenerationStep.Carver.LIQUID, 0.1F)));
        });
        this.undergroundSeagrassOnDirt = features.register("underground_seagrass_on_dirt", Feature.SIMPLE_BLOCK, feature -> {
            SimpleBlockFeatureConfig config = new SimpleBlockFeatureConfig(
                    Blocks.SEAGRASS.getDefaultState(),
                    ImmutableList.of(Blocks.DIRT.getDefaultState()),
                    ImmutableList.of(Blocks.WATER.getDefaultState()),
                    ImmutableList.of(Blocks.WATER.getDefaultState())
            );
            return feature.configure(config).decorate(Decorator.CARVING_MASK.configure(new CarvingMaskDecoratorConfig(GenerationStep.Carver.LIQUID, 0.5F)));
        });
        this.undergroundSeaPickles = features.noConfig("underground_sea_pickles", TropicraftFeatures.UNDERGROUND_SEA_PICKLE, feature -> {
            return feature.decorate(Decorator.CARVING_MASK.configure(new CarvingMaskDecoratorConfig(GenerationStep.Carver.LIQUID, 0.05F)));
        });

        this.azurite = features.register("azurite", Feature.ORE, f -> {
            return f.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, TropicraftBlocks.AZURITE_ORE.getDefaultState(), 8))
                    .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(100, 0, 128)))
                    .spreadHorizontally().repeat(3);
        });
        this.eudialyte = features.register("eudialyte", Feature.ORE, f -> {
            return f.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, TropicraftBlocks.EUDIALYTE_ORE.getDefaultState(), 12))
                    .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(100, 0, 128)))
                    .spreadHorizontally().repeat(10);
        });
        this.zircon = features.register("zircon", Feature.ORE, f -> {
            return f.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, TropicraftBlocks.ZIRCON_ORE.getDefaultState(), 14))
                    .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(100, 0, 128)))
                    .spreadHorizontally().repeat(15);
        });
        this.manganese = features.register("manganese", Feature.ORE, f -> {
            return f.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, TropicraftBlocks.MANGANESE_ORE.getDefaultState(), 10))
                    .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(32, 0, 32)))
                    .spreadHorizontally().repeat(8);
        });
        this.shaka = features.register("shaka", Feature.ORE, f -> {
            return f.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, TropicraftBlocks.SHAKA_ORE.getDefaultState(), 8))
                    .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 32)))
                    .spreadHorizontally().repeat(6);
        });

        // 0 = south
        // 90 = east
        // 180 = north
        // 270 = west
        this.homeTreeBranchSouth = features.homeTreeBranch("home_tree/branch/south", -30, 30);
        this.homeTreeBranchSouthExact = features.homeTreeBranch("home_tree/branch/south_exact", 0, 0);
        this.homeTreeBranchSouthEast = features.homeTreeBranch("home_tree/branch/southeast", 30, 60);
        this.homeTreeBranchSouthEastExact = features.homeTreeBranch("home_tree/branch/southeast_exact", 45, 45);
        this.homeTreeBranchEast = features.homeTreeBranch("home_tree/branch/east", 60, 120);
        this.homeTreeBranchEastExact = features.homeTreeBranch("home_tree/branch/east_exact", 90, 90);
        this.homeTreeBranchNorthEast = features.homeTreeBranch("home_tree/branch/northeast", 120, 150);
        this.homeTreeBranchNorthEastExact = features.homeTreeBranch("home_tree/branch/northeast_exact", 135, 135);
        this.homeTreeBranchNorth = features.homeTreeBranch("home_tree/branch/north", 150, 210);
        this.homeTreeBranchNorthExact = features.homeTreeBranch("home_tree/branch/north_exact", 180, 180);
        this.homeTreeBranchNorthWest = features.homeTreeBranch("home_tree/branch/northwest", 210, 240);
        this.homeTreeBranchNorthWestExact = features.homeTreeBranch("home_tree/branch/northwest_exact", 225, 225);
        this.homeTreeBranchWest = features.homeTreeBranch("home_tree/branch/west", 240, 300);
        this.homeTreeBranchWestExact = features.homeTreeBranch("home_tree/branch/west_exact", 270, 270);
        this.homeTreeBranchSouthWest = features.homeTreeBranch("home_tree/branch/southwest", 300, 330);
        this.homeTreeBranchSouthWestExact = features.homeTreeBranch("home_tree/branch/southwest_exact", 315, 315);
    }

     */

    public static void configuredFeatureInit() {
        grapefruitTree = fruitTree("grapefruit_tree", TropicraftBlocks.GRAPEFRUIT_SAPLING, TropicraftBlocks.GRAPEFRUIT_LEAVES);
        orangeTree = fruitTree("orange_tree", TropicraftBlocks.ORANGE_SAPLING, TropicraftBlocks.ORANGE_LEAVES);
        lemonTree = fruitTree("lemon_tree", TropicraftBlocks.LEMON_SAPLING, TropicraftBlocks.LEMON_LEAVES);
        limeTree = fruitTree("lime_tree", TropicraftBlocks.LIME_SAPLING, TropicraftBlocks.LIME_LEAVES);

        normalPalmTree = sparseTree("normal_palm_tree", TropicraftFeatures.NORMAL_PALM_TREE, FeatureConfig.DEFAULT, 0.2F);
        curvedPalmTree = sparseTree("curved_palm_tree", TropicraftFeatures.CURVED_PALM_TREE, FeatureConfig.DEFAULT, 0.2F);
        largePalmTree = sparseTree("large_palm_tree", TropicraftFeatures.LARGE_PALM_TREE, FeatureConfig.DEFAULT, 0.2F);

        rainforestUpTree = sparseTree("rainforest_up_tree", TropicraftFeatures.UP_TREE, FeatureConfig.DEFAULT, 0.2F);
        rainforestSmallTualung = sparseTree("rainforest_small_tualung", TropicraftFeatures.SMALL_TUALUNG, FeatureConfig.DEFAULT, 0.3F);
        rainforestLargeTualung = sparseTree("rainforest_large_tualung", TropicraftFeatures.LARGE_TUALUNG, FeatureConfig.DEFAULT, 0.4F);
        rainforestTallTree = sparseTree("rainforest_tall_tree", TropicraftFeatures.TALL_TREE, FeatureConfig.DEFAULT, 0.5F);
        rainforestVines = register("rainforest_vines", TropicraftFeatures.VINES,
                f -> f.configure(new RainforestVinesConfig()).spreadHorizontally().repeat(50)
        );

        eih = noConfig("eih", TropicraftFeatures.EIH,
                f -> f.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                        .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.01F, 1)))
        );

        pineapplePatch = register("pineapple_patch", Feature.RANDOM_PATCH, feature -> {
            SimpleBlockStateProvider stateProvider = new SimpleBlockStateProvider(TropicraftBlocks.PINEAPPLE.getDefaultState());
            RandomPatchFeatureConfig config = new RandomPatchFeatureConfig.Builder(stateProvider, DoublePlantPlacer.INSTANCE).tries(64).cannotProject().build();
            return feature.configure(config).decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP);
        });
        tropicsFlowers = register("tropics_flowers", Feature.FLOWER, feature -> {
            //BlockStateProvider stateProvider = new NoiseFromTagBlockStateProvider(TropicraftTags.Blocks.TROPICS_FLOWERS);
            BlockStateProvider stateProvider = new NoiseFromTagTropicFlowerBlockStateProvider();
            RandomPatchFeatureConfig config = new RandomPatchFeatureConfig.Builder(stateProvider, SimpleBlockPlacer.INSTANCE).tries(64).build();
            return feature.configure(config).decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(12));
        });

        rainforestFlowers = register("rainforest_flowers", Feature.FLOWER, feature -> {
            //BlockStateProvider stateProvider = new NoiseFromTagBlockStateProvider(TropicraftTags.Blocks.RAINFOREST_FLOWERS);
            BlockStateProvider stateProvider = new NoiseFromTagRainForestFlowerBlockStateProvider();
            RandomPatchFeatureConfig config = new RandomPatchFeatureConfig.Builder(stateProvider, SimpleBlockPlacer.INSTANCE).tries(64).cannotProject().build();
            return feature.configure(config).decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(4));
        });
        irisFlowers = register("iris_flowers", Feature.RANDOM_PATCH, feature -> {
            BlockStateProvider stateProvider = new SimpleBlockStateProvider(TropicraftBlocks.IRIS.getDefaultState());
            RandomPatchFeatureConfig config = new RandomPatchFeatureConfig.Builder(stateProvider, DoublePlantPlacer.INSTANCE).tries(64).cannotProject().build();
            return feature.configure(config).decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(10));
        });

        coffeeBush = noConfig("coffee_bush", TropicraftFeatures.COFFEE_BUSH, feature -> {
            return feature.decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(5));
        });
        undergrowth = noConfig("undergrowth", TropicraftFeatures.UNDERGROWTH, feature -> {
            return feature.decorate(ConfiguredFeatures.Decorators.SPREAD_32_ABOVE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(100));
        });

        undergroundSeagrassOnStone = register("underground_seagrass_on_stone", Feature.SIMPLE_BLOCK, feature -> {
            SimpleBlockFeatureConfig config = new SimpleBlockFeatureConfig(
                    new SimpleBlockStateProvider(Blocks.SEAGRASS.getDefaultState()),
                    ImmutableList.of(Blocks.STONE.getDefaultState()),
                    ImmutableList.of(Blocks.WATER.getDefaultState()),
                    ImmutableList.of(Blocks.WATER.getDefaultState())
            );
            return feature.configure(config).applyChance(10).decorate(Decorator.CARVING_MASK.configure(new CarvingMaskDecoratorConfig(GenerationStep.Carver.LIQUID)));
        });
        undergroundSeagrassOnDirt = register("underground_seagrass_on_dirt", Feature.SIMPLE_BLOCK, feature -> {
            SimpleBlockFeatureConfig config = new SimpleBlockFeatureConfig(
                    new SimpleBlockStateProvider(Blocks.SEAGRASS.getDefaultState()),
                    ImmutableList.of(Blocks.DIRT.getDefaultState()),
                    ImmutableList.of(Blocks.WATER.getDefaultState()),
                    ImmutableList.of(Blocks.WATER.getDefaultState())
            );
            return feature.configure(config).applyChance(5).decorate(Decorator.CARVING_MASK.configure(new CarvingMaskDecoratorConfig(GenerationStep.Carver.LIQUID)));//.decorate(Decorator.CARVING_MASK.configure(new CarvingMaskDecoratorConfig(GenerationStep.Carver.LIQUID, 0.5F)));
        });
        undergroundSeaPickles = noConfig("underground_sea_pickles", TropicraftFeatures.UNDERGROUND_SEA_PICKLE, feature -> {
            return feature.applyChance(5).decorate(Decorator.CARVING_MASK.configure(new CarvingMaskDecoratorConfig(GenerationStep.Carver.LIQUID)));//.decorate(Decorator.CARVING_MASK.configure(new CarvingMaskDecoratorConfig(GenerationStep.Carver.LIQUID, 0.05F)));
        });

        azurite = register("azurite", Feature.ORE, f -> {
            return f.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, TropicraftBlocks.AZURITE_ORE.getDefaultState(), 8))
                    .uniformRange(YOffset.fixed(100), YOffset.fixed(128))
                    //.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(100, 0, 128)))
                    .spreadHorizontally().repeat(3);
        });
        eudialyte = register("eudialyte", Feature.ORE, f -> {
            return f.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, TropicraftBlocks.EUDIALYTE_ORE.getDefaultState(), 12))
                    .uniformRange(YOffset.fixed(100), YOffset.fixed(128))
                    //.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(100, 0, 128)))
                    .spreadHorizontally().repeat(10);
        });
        zircon = register("zircon", Feature.ORE, f -> {
            return f.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, TropicraftBlocks.ZIRCON_ORE.getDefaultState(), 14))
                    .uniformRange(YOffset.fixed(100), YOffset.fixed(128))
                    //.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(100, 0, 128)))
                    .spreadHorizontally().repeat(15);
        });
        manganese = register("manganese", Feature.ORE, f -> {
            return f.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, TropicraftBlocks.MANGANESE_ORE.getDefaultState(), 10))
                    .uniformRange(YOffset.fixed(32), YOffset.fixed(32))
                    //.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(32, 0, 32)))
                    .spreadHorizontally().repeat(8);
        });
        shaka = register("shaka", Feature.ORE, f -> {
            return f.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, TropicraftBlocks.SHAKA_ORE.getDefaultState(), 8))
                    .uniformRange(YOffset.fixed(0), YOffset.fixed(32))
                    //.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 32)))
                    .spreadHorizontally().repeat(6);
        });

        // 0 = south
        // 90 = east
        // 180 = north
        // 270 = west
        homeTreeBranchSouth = homeTreeBranch("home_tree/branch/south", -30, 30);
        homeTreeBranchSouthExact = homeTreeBranch("home_tree/branch/south_exact", 0, 0);
        homeTreeBranchSouthEast = homeTreeBranch("home_tree/branch/southeast", 30, 60);
        homeTreeBranchSouthEastExact = homeTreeBranch("home_tree/branch/southeast_exact", 45, 45);
        homeTreeBranchEast = homeTreeBranch("home_tree/branch/east", 60, 120);
        homeTreeBranchEastExact = homeTreeBranch("home_tree/branch/east_exact", 90, 90);
        homeTreeBranchNorthEast = homeTreeBranch("home_tree/branch/northeast", 120, 150);
        homeTreeBranchNorthEastExact = homeTreeBranch("home_tree/branch/northeast_exact", 135, 135);
        homeTreeBranchNorth = homeTreeBranch("home_tree/branch/north", 150, 210);
        homeTreeBranchNorthExact = homeTreeBranch("home_tree/branch/north_exact", 180, 180);
        homeTreeBranchNorthWest = homeTreeBranch("home_tree/branch/northwest", 210, 240);
        homeTreeBranchNorthWestExact = homeTreeBranch("home_tree/branch/northwest_exact", 225, 225);
        homeTreeBranchWest = homeTreeBranch("home_tree/branch/west", 240, 300);
        homeTreeBranchWestExact = homeTreeBranch("home_tree/branch/west_exact", 270, 270);
        homeTreeBranchSouthWest = homeTreeBranch("home_tree/branch/southwest", 300, 330);
        homeTreeBranchSouthWestExact = homeTreeBranch("home_tree/branch/southwest_exact", 315, 315);
    }

    public static void addFruitTrees(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.grapefruitTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.orangeTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.lemonTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.limeTree);
    }

    public static void addPalmTrees(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.normalPalmTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.curvedPalmTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.largePalmTree);
    }

    public static void addRainforestTrees(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestUpTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestSmallTualung);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestLargeTualung);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestTallTree);
    }

    public static void addRainforestPlants(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_MELON);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestVines);
    }

    public static void addPineapples(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.pineapplePatch);
    }

    public static void addEih(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.eih);
    }

    public static void addTropicsFlowers(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.tropicsFlowers);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.irisFlowers);
    }

    public static void addTropicsGems(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, TropicraftConfiguredFeatures.azurite);
        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, TropicraftConfiguredFeatures.eudialyte);
        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, TropicraftConfiguredFeatures.zircon);
    }

    public static void addTropicsMetals(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, TropicraftConfiguredFeatures.manganese);
        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, TropicraftConfiguredFeatures.shaka);
    }

    public static void addUndergroundSeagrass(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.undergroundSeagrassOnStone);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.undergroundSeagrassOnDirt);
    }

    public static void addUndergroundPickles(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.undergroundSeaPickles);
    }


    public static <FC extends FeatureConfig, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF registerConfiguredFeature(String id, CF configuredFeature) {
        Identifier tropicID = new Identifier(Constants.MODID, id);
        if (BuiltinRegistries.CONFIGURED_FEATURE.getIds().contains(tropicID))
            throw new IllegalStateException("Configured Feature ID: \"" + tropicID.toString() + "\" already exists in the Configured Features registry!");


        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, tropicID, configuredFeature);
    }

    public static <F extends Feature<?>> ConfiguredFeature<?, ?> register(String id, F feature, Function<F, ConfiguredFeature<?, ?>> configure) {
        //return this.worldgen.register(new Identifier(Constants.MODID, id), configure.apply(feature));
        Identifier tropicID = new Identifier(Constants.MODID, id);
        if (BuiltinRegistries.CONFIGURED_FEATURE.getIds().contains(tropicID))
            throw new IllegalStateException("Configured Feature ID: \"" + tropicID.toString() + "\" already exists in the Configured Features registry!");

        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Constants.MODID, id), configure.apply(feature));
    }

    /*
    public <F extends Feature<?>> ConfiguredFeature<?, ?> register(String id, F feature, Function<F, ConfiguredFeature<?, ?>> configure) {
        return this.register(id, feature, configure);
    }
     */

    public static <F extends Feature<DefaultFeatureConfig>> ConfiguredFeature<?, ?> noConfig(String id, F feature, UnaryOperator<ConfiguredFeature<?, ?>> configure) {
        return register(id, feature, f -> configure.apply(f.configure(DefaultFeatureConfig.INSTANCE)));
    }

    public static ConfiguredFeature<?, ?> fruitTree(String id, Block sapling, Block fruitLeaves) {
        return sparseTree(id, TropicraftFeatures.FRUIT_TREE, new FruitTreeConfig(sapling, fruitLeaves), 0.2F);
    }

    public static <C extends FeatureConfig, F extends Feature<C>> ConfiguredFeature<?, ?> sparseTree(String id, F feature, C config, float chance) {
        return register(id, feature, f -> {
            return f.configure(config).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                    .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, chance, 1)));
        });
    }

    public static <C extends FeatureConfig, F extends Feature<C>> ConfiguredFeature<?, ?> homeTreeBranch(String id, float minAngle, float maxAngle) {
        return register(id, TropicraftFeatures.HOME_TREE_BRANCH,
                f -> f.configure(new HomeTreeBranchConfig(minAngle, maxAngle))
        );
    }

}
