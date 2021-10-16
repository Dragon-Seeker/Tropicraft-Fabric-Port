package net.tropicraft.core.common.dimension.feature;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.loom.configuration.FabricApiExtension;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.DoublePlantPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.CarvingMaskDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
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

public final class TropicraftConfiguredFeatures extends Features{
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

        normalPalmTree = sparseTree("normal_palm_tree", TropicraftFeatures.NORMAL_PALM_TREE, FeatureConfiguration.NONE, 0.2F);
        curvedPalmTree = sparseTree("curved_palm_tree", TropicraftFeatures.CURVED_PALM_TREE, FeatureConfiguration.NONE, 0.2F);
        largePalmTree = sparseTree("large_palm_tree", TropicraftFeatures.LARGE_PALM_TREE, FeatureConfiguration.NONE, 0.2F);

        rainforestUpTree = sparseTree("rainforest_up_tree", TropicraftFeatures.UP_TREE, FeatureConfiguration.NONE, 0.2F);
        rainforestSmallTualung = sparseTree("rainforest_small_tualung", TropicraftFeatures.SMALL_TUALUNG, FeatureConfiguration.NONE, 0.3F);
        rainforestLargeTualung = sparseTree("rainforest_large_tualung", TropicraftFeatures.LARGE_TUALUNG, FeatureConfiguration.NONE, 0.4F);
        rainforestTallTree = sparseTree("rainforest_tall_tree", TropicraftFeatures.TALL_TREE, FeatureConfiguration.NONE, 0.5F);
        rainforestVines = register("rainforest_vines", TropicraftFeatures.VINES,
                f -> f.configured(new RainforestVinesConfig()).squared().count(50)
        );

        eih = noConfig("eih", TropicraftFeatures.EIH,
                f -> f.decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                        .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.01F, 1)))
        );

        pineapplePatch = register("pineapple_patch", Feature.RANDOM_PATCH, feature -> {
            SimpleStateProvider stateProvider = new SimpleStateProvider(TropicraftBlocks.PINEAPPLE.defaultBlockState());
            RandomPatchConfiguration config = new RandomPatchConfiguration.GrassConfigurationBuilder(stateProvider, DoublePlantPlacer.INSTANCE).tries(64).noProjection().build();
            return feature.configured(config).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE);
        });
        tropicsFlowers = register("tropics_flowers", Feature.FLOWER, feature -> {
            //BlockStateProvider stateProvider = new NoiseFromTagBlockStateProvider(TropicraftTags.Blocks.TROPICS_FLOWERS);
            BlockStateProvider stateProvider = new NoiseFromTagTropicFlowerBlockStateProvider();
            RandomPatchConfiguration config = new RandomPatchConfiguration.GrassConfigurationBuilder(stateProvider, SimpleBlockPlacer.INSTANCE).tries(64).build();
            return feature.configured(config).decorated(Features.Decorators.ADD_32.decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(12));
        });

        rainforestFlowers = register("rainforest_flowers", Feature.FLOWER, feature -> {
            //BlockStateProvider stateProvider = new NoiseFromTagBlockStateProvider(TropicraftTags.Blocks.RAINFOREST_FLOWERS);
            BlockStateProvider stateProvider = new NoiseFromTagRainForestFlowerBlockStateProvider();
            RandomPatchConfiguration config = new RandomPatchConfiguration.GrassConfigurationBuilder(stateProvider, SimpleBlockPlacer.INSTANCE).tries(64).noProjection().build();
            return feature.configured(config).decorated(Features.Decorators.ADD_32.decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4));
        });
        irisFlowers = register("iris_flowers", Feature.RANDOM_PATCH, feature -> {
            BlockStateProvider stateProvider = new SimpleStateProvider(TropicraftBlocks.IRIS.defaultBlockState());
            RandomPatchConfiguration config = new RandomPatchConfiguration.GrassConfigurationBuilder(stateProvider, DoublePlantPlacer.INSTANCE).tries(64).noProjection().build();
            return feature.configured(config).decorated(Features.Decorators.ADD_32.decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(10));
        });

        coffeeBush = noConfig("coffee_bush", TropicraftFeatures.COFFEE_BUSH, feature -> {
            return feature.decorated(Features.Decorators.ADD_32.decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(5));
        });
        undergrowth = noConfig("undergrowth", TropicraftFeatures.UNDERGROWTH, feature -> {
            return feature.decorated(Features.Decorators.ADD_32.decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(100));
        });

        undergroundSeagrassOnStone = register("underground_seagrass_on_stone", Feature.SIMPLE_BLOCK, feature -> {
            SimpleBlockConfiguration config = new SimpleBlockConfiguration(
                    new SimpleStateProvider(Blocks.SEAGRASS.defaultBlockState()),
                    ImmutableList.of(Blocks.STONE.defaultBlockState()),
                    ImmutableList.of(Blocks.WATER.defaultBlockState()),
                    ImmutableList.of(Blocks.WATER.defaultBlockState())
            );
            return feature.configured(config).rarity(10).decorated(FeatureDecorator.CARVING_MASK.configured(new CarvingMaskDecoratorConfiguration(GenerationStep.Carving.LIQUID)));
        });
        undergroundSeagrassOnDirt = register("underground_seagrass_on_dirt", Feature.SIMPLE_BLOCK, feature -> {
            SimpleBlockConfiguration config = new SimpleBlockConfiguration(
                    new SimpleStateProvider(Blocks.SEAGRASS.defaultBlockState()),
                    ImmutableList.of(Blocks.DIRT.defaultBlockState()),
                    ImmutableList.of(Blocks.WATER.defaultBlockState()),
                    ImmutableList.of(Blocks.WATER.defaultBlockState())
            );
            return feature.configured(config).rarity(5).decorated(FeatureDecorator.CARVING_MASK.configured(new CarvingMaskDecoratorConfiguration(GenerationStep.Carving.LIQUID)));//.decorate(Decorator.CARVING_MASK.configure(new CarvingMaskDecoratorConfig(GenerationStep.Carver.LIQUID, 0.5F)));
        });
        undergroundSeaPickles = noConfig("underground_sea_pickles", TropicraftFeatures.UNDERGROUND_SEA_PICKLE, feature -> {
            return feature.rarity(5).decorated(FeatureDecorator.CARVING_MASK.configured(new CarvingMaskDecoratorConfiguration(GenerationStep.Carving.LIQUID)));//.decorate(Decorator.CARVING_MASK.configure(new CarvingMaskDecoratorConfig(GenerationStep.Carver.LIQUID, 0.05F)));
        });

        azurite = register("azurite", Feature.ORE, f -> {
            return f.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, TropicraftBlocks.AZURITE_ORE.defaultBlockState(), 8))
                    .rangeUniform(VerticalAnchor.absolute(100), VerticalAnchor.absolute(128))
                    //.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(100, 0, 128)))
                    .squared().count(3);
        });
        eudialyte = register("eudialyte", Feature.ORE, f -> {
            return f.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, TropicraftBlocks.EUDIALYTE_ORE.defaultBlockState(), 12))
                    .rangeUniform(VerticalAnchor.absolute(100), VerticalAnchor.absolute(128))
                    //.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(100, 0, 128)))
                    .squared().count(10);
        });
        zircon = register("zircon", Feature.ORE, f -> {
            return f.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, TropicraftBlocks.ZIRCON_ORE.defaultBlockState(), 14))
                    .rangeUniform(VerticalAnchor.absolute(100), VerticalAnchor.absolute(128))
                    //.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(100, 0, 128)))
                    .squared().count(15);
        });
        manganese = register("manganese", Feature.ORE, f -> {
            return f.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, TropicraftBlocks.MANGANESE_ORE.defaultBlockState(), 10))
                    .rangeUniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(32))
                    //.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(32, 0, 32)))
                    .squared().count(8);
        });
        shaka = register("shaka", Feature.ORE, f -> {
            return f.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, TropicraftBlocks.SHAKA_ORE.defaultBlockState(), 8))
                    .rangeUniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(32))
                    //.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 32)))
                    .squared().count(6);
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

    public static void addFruitTrees(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.grapefruitTree);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.orangeTree);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.lemonTree);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.limeTree);
    }

    public static void addPalmTrees(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.normalPalmTree);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.curvedPalmTree);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.largePalmTree);
    }

    public static void addRainforestTrees(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestUpTree);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestSmallTualung);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestLargeTualung);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestTallTree);
    }

    public static void addRainforestPlants(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_MELON);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestVines);
    }

    public static void addPineapples(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.pineapplePatch);
    }

    public static void addEih(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.eih);
    }

    public static void addTropicsFlowers(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.tropicsFlowers);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.irisFlowers);
    }

    public static void addTropicsGems(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TropicraftConfiguredFeatures.azurite);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TropicraftConfiguredFeatures.eudialyte);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TropicraftConfiguredFeatures.zircon);
    }

    public static void addTropicsMetals(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TropicraftConfiguredFeatures.manganese);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TropicraftConfiguredFeatures.shaka);
    }

    public static void addUndergroundSeagrass(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.undergroundSeagrassOnStone);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.undergroundSeagrassOnDirt);
    }

    public static void addUndergroundPickles(BiomeGenerationSettings.Builder generation) {
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.undergroundSeaPickles);
    }


    public static <FC extends FeatureConfiguration, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF registerConfiguredFeature(String id, CF configuredFeature) {
        ResourceLocation tropicID = new ResourceLocation(Constants.MODID, id);
        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(tropicID))
            throw new IllegalStateException("Configured Feature ID: \"" + tropicID.toString() + "\" already exists in the Configured Features registry!");


        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, tropicID, configuredFeature);
    }

    public static <F extends Feature<?>> ConfiguredFeature<?, ?> register(String id, F feature, Function<F, ConfiguredFeature<?, ?>> configure) {
        //return this.worldgen.register(new Identifier(Constants.MODID, id), configure.apply(feature));
        ResourceLocation tropicID = new ResourceLocation(Constants.MODID, id);
        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(tropicID))
            throw new IllegalStateException("Configured Feature ID: \"" + tropicID.toString() + "\" already exists in the Configured Features registry!");

        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Constants.MODID, id), configure.apply(feature));
    }

    /*
    public <F extends Feature<?>> ConfiguredFeature<?, ?> register(String id, F feature, Function<F, ConfiguredFeature<?, ?>> configure) {
        return this.register(id, feature, configure);
    }
     */

    public static <F extends Feature<NoneFeatureConfiguration>> ConfiguredFeature<?, ?> noConfig(String id, F feature, UnaryOperator<ConfiguredFeature<?, ?>> configure) {
        return register(id, feature, f -> configure.apply(f.configured(NoneFeatureConfiguration.INSTANCE)));
    }

    public static ConfiguredFeature<?, ?> fruitTree(String id, Block sapling, Block fruitLeaves) {
        return sparseTree(id, TropicraftFeatures.FRUIT_TREE, new FruitTreeConfig(sapling, fruitLeaves), 0.2F);
    }

    public static <C extends FeatureConfiguration, F extends Feature<C>> ConfiguredFeature<?, ?> sparseTree(String id, F feature, C config, float chance) {
        return register(id, feature, f -> {
            return f.configured(config).decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                    .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, chance, 1)));
        });
    }

    public static <C extends FeatureConfiguration, F extends Feature<C>> ConfiguredFeature<?, ?> homeTreeBranch(String id, float minAngle, float maxAngle) {
        return register(id, TropicraftFeatures.HOME_TREE_BRANCH,
                f -> f.configured(new HomeTreeBranchConfig(minAngle, maxAngle))
        );
    }

}
