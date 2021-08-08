package net.tropicraftFabric.common.dimension.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CarvingMaskDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.client.data.TropicraftTags;
import net.tropicraftFabric.client.data.WorldgenDataConsumer;
import net.tropicraftFabric.common.dimension.feature.block_state_provider.NoiseFromTagBlockStateProvider;
import net.tropicraftFabric.common.dimension.feature.config.FruitTreeConfig;
import net.tropicraftFabric.common.dimension.feature.config.HomeTreeBranchConfig;
import net.tropicraftFabric.common.dimension.feature.config.RainforestVinesConfig;
import net.tropicraftFabric.common.registry.TropicraftBlocks;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class TropicraftConfiguredFeatures {
    public final ConfiguredFeature<?, ?> grapefruitTree;
    public final ConfiguredFeature<?, ?> orangeTree;
    public final ConfiguredFeature<?, ?> lemonTree;
    public final ConfiguredFeature<?, ?> limeTree;
    public final ConfiguredFeature<?, ?> normalPalmTree;
    public final ConfiguredFeature<?, ?> curvedPalmTree;
    public final ConfiguredFeature<?, ?> largePalmTree;
    public final ConfiguredFeature<?, ?> rainforestUpTree;
    public final ConfiguredFeature<?, ?> rainforestSmallTualung;
    public final ConfiguredFeature<?, ?> rainforestLargeTualung;
    public final ConfiguredFeature<?, ?> rainforestTallTree;
    public final ConfiguredFeature<?, ?> rainforestVines;
    public final ConfiguredFeature<?, ?> eih;

    public final ConfiguredFeature<?, ?> pineapplePatch;
    public final ConfiguredFeature<?, ?> tropicsFlowers;
    public final ConfiguredFeature<?, ?> rainforestFlowers;
    public final ConfiguredFeature<?, ?> irisFlowers;

    public final ConfiguredFeature<?, ?> coffeeBush;
    public final ConfiguredFeature<?, ?> undergrowth;

    public final ConfiguredFeature<?, ?> undergroundSeagrassOnStone;
    public final ConfiguredFeature<?, ?> undergroundSeagrassOnDirt;
    public final ConfiguredFeature<?, ?> undergroundSeaPickles;

    public final ConfiguredFeature<?, ?> azurite;
    public final ConfiguredFeature<?, ?> eudialyte;
    public final ConfiguredFeature<?, ?> zircon;
    public final ConfiguredFeature<?, ?> manganese;
    public final ConfiguredFeature<?, ?> shaka;

    public final ConfiguredFeature<?, ?> homeTreeBranchSouth;
    public final ConfiguredFeature<?, ?> homeTreeBranchSouthExact;
    public final ConfiguredFeature<?, ?> homeTreeBranchSouthEast;
    public final ConfiguredFeature<?, ?> homeTreeBranchSouthEastExact;
    public final ConfiguredFeature<?, ?> homeTreeBranchEast;
    public final ConfiguredFeature<?, ?> homeTreeBranchEastExact;
    public final ConfiguredFeature<?, ?> homeTreeBranchNorthEast;
    public final ConfiguredFeature<?, ?> homeTreeBranchNorthEastExact;
    public final ConfiguredFeature<?, ?> homeTreeBranchNorth;
    public final ConfiguredFeature<?, ?> homeTreeBranchNorthExact;
    public final ConfiguredFeature<?, ?> homeTreeBranchNorthWest;
    public final ConfiguredFeature<?, ?> homeTreeBranchNorthWestExact;
    public final ConfiguredFeature<?, ?> homeTreeBranchWest;
    public final ConfiguredFeature<?, ?> homeTreeBranchWestExact;
    public final ConfiguredFeature<?, ?> homeTreeBranchSouthWest;
    public final ConfiguredFeature<?, ?> homeTreeBranchSouthWestExact;

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

    public void addFruitTrees(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.grapefruitTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.orangeTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.lemonTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.limeTree);
    }

    public void addPalmTrees(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.normalPalmTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.curvedPalmTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.largePalmTree);
    }

    public void addRainforestTrees(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.rainforestUpTree);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.rainforestSmallTualung);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.rainforestLargeTualung);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.rainforestTallTree);
    }

    public void addRainforestPlants(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_MELON);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.rainforestVines);
    }

    public void addPineapples(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.pineapplePatch);
    }

    public void addEih(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.eih);
    }

    public void addTropicsFlowers(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.tropicsFlowers);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.irisFlowers);
    }

    public void addTropicsGems(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, this.azurite);
        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, this.eudialyte);
        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, this.zircon);
    }

    public void addTropicsMetals(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, this.manganese);
        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, this.shaka);
    }

    public void addUndergroundSeagrass(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.undergroundSeagrassOnStone);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.undergroundSeagrassOnDirt);
    }

    public void addUndergroundPickles(GenerationSettings.Builder generation) {
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, this.undergroundSeaPickles);
    }

    static final class Register {
        public static <FC extends FeatureConfig, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF registerConfiguredFeature(String id, CF configuredFeature) {
            Identifier tropicID = new Identifier(Constants.MODID, id);
            if (BuiltinRegistries.CONFIGURED_FEATURE.getIds().contains(tropicID))
                throw new IllegalStateException("Configured Feature ID: \"" + tropicID.toString() + "\" already exists in the Configured Features registry!");

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, tropicID, configuredFeature);
            return configuredFeature;
        }




        private final WorldgenDataConsumer<ConfiguredFeature<?, ?>> worldgen;

        Register(WorldgenDataConsumer<ConfiguredFeature<?, ?>> worldgen) {
            this.worldgen = worldgen;
        }

        public <F extends Feature<?>> ConfiguredFeature<?, ?> register(String id, F feature, Function<F, ConfiguredFeature<?, ?>> configure) {
            return this.worldgen.register(new Identifier(Constants.MODID, id), configure.apply(feature));
        }

        /*
        public <F extends Feature<?>> ConfiguredFeature<?, ?> register(String id, F feature, Function<F, ConfiguredFeature<?, ?>> configure) {
            return this.register(id, feature, configure);
        }
         */

        public <F extends Feature<DefaultFeatureConfig>> ConfiguredFeature<?, ?> noConfig(String id, F feature, UnaryOperator<ConfiguredFeature<?, ?>> configure) {
            return this.register(id, feature, f -> configure.apply(f.configure(DefaultFeatureConfig.INSTANCE)));
        }

        public ConfiguredFeature<?, ?> fruitTree(String id, Block sapling, Block fruitLeaves) {
            return this.sparseTree(id, TropicraftFeatures.FRUIT_TREE, new FruitTreeConfig(sapling, fruitLeaves), 0.2F);
        }

        public <C extends FeatureConfig, F extends Feature<C>> ConfiguredFeature<?, ?> sparseTree(String id, F feature, C config, float chance) {
            return this.register(id, feature, f -> {
                return f.configure(config).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                        .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, chance, 1)));
            });
        }

        public <C extends FeatureConfig, F extends Feature<C>> ConfiguredFeature<?, ?> homeTreeBranch(String id, float minAngle, float maxAngle) {
            return this.register(id, TropicraftFeatures.HOME_TREE_BRANCH,
                    f -> f.configure(new HomeTreeBranchConfig(minAngle, maxAngle))
            );
        }
    }
}
