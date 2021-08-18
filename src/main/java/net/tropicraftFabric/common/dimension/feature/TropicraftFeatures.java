package net.tropicraftFabric.common.dimension.feature;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePool.Projection;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.dimension.feature.config.FruitTreeConfig;
import net.tropicraftFabric.common.dimension.feature.config.HomeTreeBranchConfig;
import net.tropicraftFabric.common.dimension.feature.config.RainforestVinesConfig;
import net.tropicraftFabric.common.dimension.feature.jigsaw.SinkInGroundProcessor;
import net.tropicraftFabric.common.dimension.feature.jigsaw.SmoothingGravityProcessor;
import net.tropicraftFabric.common.dimension.feature.jigsaw.SteepPathProcessor;
import net.tropicraftFabric.common.dimension.feature.jigsaw.StructureSupportsProcessor;
import net.tropicraftFabric.common.registry.TropicraftBlocks;
import net.tropicraftFabric.mixins.ProjectionFactory;
import net.tropicraftFabric.mixins.StructureFeatureInvoker;

import java.util.ArrayList;
import java.util.List;

public class TropicraftFeatures {

    public static List<Feature<?>> features = new ArrayList<>();

    public static List<StructureFeature<?>> structureFeatures = new ArrayList<>();

    //public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Constants.MODID);
    //public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Constants.MODID);

    public static final FruitTreeFeature FRUIT_TREE = registerFeature("fruit_tree", new FruitTreeFeature(FruitTreeConfig.CODEC));
    public static final PalmTreeFeature NORMAL_PALM_TREE = registerFeature("normal_palm_tree", new NormalPalmTreeFeature(DefaultFeatureConfig.CODEC));
    public static final PalmTreeFeature CURVED_PALM_TREE = registerFeature("curved_palm_tree", new CurvedPalmTreeFeature(DefaultFeatureConfig.CODEC));
    public static final PalmTreeFeature LARGE_PALM_TREE = registerFeature("large_palm_tree", new LargePalmTreeFeature(DefaultFeatureConfig.CODEC));
    public static final RainforestTreeFeature UP_TREE = registerFeature("up_tree", new UpTreeFeature(DefaultFeatureConfig.CODEC));
    public static final RainforestTreeFeature SMALL_TUALUNG = registerFeature("small_tualung", new TualungFeature(DefaultFeatureConfig.CODEC, 16, 9));
    public static final RainforestTreeFeature LARGE_TUALUNG = registerFeature("large_tualung", new TualungFeature(DefaultFeatureConfig.CODEC, 25, 11));
    public static final RainforestTreeFeature TALL_TREE = registerFeature("tall_tree", new TallRainforestTreeFeature(DefaultFeatureConfig.CODEC));
    public static final EIHFeature EIH = registerFeature("eih", new EIHFeature(DefaultFeatureConfig.CODEC));
    public static final UndergrowthFeature UNDERGROWTH = registerFeature("undergrowth", new UndergrowthFeature(DefaultFeatureConfig.CODEC));
    public static final RainforestVinesFeature VINES = registerFeature("rainforest_vines", new RainforestVinesFeature(RainforestVinesConfig.CODEC));
    public static final UndergroundSeaPickleFeature UNDERGROUND_SEA_PICKLE = registerFeature("underground_sea_pickle", new UndergroundSeaPickleFeature(DefaultFeatureConfig.CODEC));


    public static final StructureFeature<StructurePoolFeatureConfig> KOA_VILLAGE = registerFeatureStructure("koa_village", new KoaVillageStructure(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final StructureFeature<StructurePoolFeatureConfig> HOME_TREE = registerFeatureStructure("home_tree", new HomeTreeStructure(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    public static final HomeTreeBranchFeature<HomeTreeBranchConfig> HOME_TREE_BRANCH = registerFeature("home_tree_branch", new HomeTreeBranchFeature(HomeTreeBranchConfig.CODEC));
    public static final CoffeePlantFeature COFFEE_BUSH = registerFeature("coffee_bush", new CoffeePlantFeature(DefaultFeatureConfig.CODEC));

    public static final ConfiguredFeature<?, ?> NORMAL_PALM_TREE_CONFIGURED = TropicraftConfiguredFeatures.register("normal_palm_tree_configured", TropicraftFeatures.NORMAL_PALM_TREE, f -> f.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.08F, 1))));
    public static final ConfiguredFeature<?, ?> CURVED_PALM_TREE_CONFIGURED = TropicraftConfiguredFeatures.register("curved_palm_tree_configured", TropicraftFeatures.CURVED_PALM_TREE, f -> f.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.08F, 1))));
    public static final ConfiguredFeature<?, ?> LARGE_PALM_TREE_CONFIGURED = TropicraftConfiguredFeatures.register("large_palm_tree_configured", TropicraftFeatures.LARGE_PALM_TREE, f -> f.configure(DefaultFeatureConfig.INSTANCE).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.08F, 1))));
    public static final ConfiguredFeature<?, ?> PINEAPPLE_PATCH_OVERWORLD = registerConfiguredFeature("pineapple_patch_overworld",
            Feature.RANDOM_PATCH.configure(
                    new RandomPatchFeatureConfig.Builder(
                        new SimpleBlockStateProvider(
                            TropicraftBlocks.PINEAPPLE.getDefaultState()),
                    new DoublePlantPlacer()).tries(6).canReplace().build()).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE));

    public static final RegistryKey<ConfiguredFeature<?, ?>> NORMAL_PALM_TREE_CONFIGURED_KEY = registerConfiguredFeatureKey("normal_palm_tree_configured");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CURVED_PALM_TREE_CONFIGURED_KEY = registerConfiguredFeatureKey("curved_palm_tree_configured");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LARGE_PALM_TREE_CONFIGURED_KEY = registerConfiguredFeatureKey("large_palm_tree_configured");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PINEAPPLE_PATCH_OVERWORLD_KEY = registerConfiguredFeatureKey("pineapple_patch_overworld");




    /*
    public static final Projection KOA_PATH =
            Projection.create("KOA_PATH",
                              Constants.MODID + ":koa_path",
                              ImmutableList.of(new SmoothingGravityProcessor(Heightmap.Type.WORLD_SURFACE_WG, -1), new SinkInGroundProcessor(), new SteepPathProcessor(), new StructureSupportsProcessor(false, ImmutableList.of(new Identifier("bamboo_fence")))); //ImmutableList.of(TropicBlocks.BAMBOO_FENCE)
     */

    public static final Projection KOA_PATH = ProjectionFactory.newProjection("KOA_PATH",
            2,
            Constants.MODID + ":koa_path",
            ImmutableList.of(new SmoothingGravityProcessor(Heightmap.Type.WORLD_SURFACE_WG, -1),
                             new SinkInGroundProcessor(),
                             new SteepPathProcessor(),
                             new StructureSupportsProcessor(false, ImmutableList.of(new Identifier("bamboo_fence")))));

    public static void setStructurePoolsProjectionMap(){
        Projection.PROJECTIONS_BY_ID.put(KOA_PATH.getId(), KOA_PATH);
    }



    /*
    private static <T extends Feature<?>> T register(final String name, final Supplier<T> sup) {
        return FEATURES.register(name, sup);
    }

    private static <T extends StructureFeature<?>> T registerStructure(final String name, T structure, GenerationStep.Feature step) {
        StructureFeature.STRUCTURES.put("tropicraft:" + name, structure);
        StructureFeature.STRUCTURE_TO_GENERATION_STEP.put(structure, step);
        return STRUCTURES.register(name, () -> structure);
    }
     */

    public static RegistryKey<ConfiguredFeature<?, ?>> registerConfiguredFeatureKey(String id){
        Identifier tropicID = new Identifier(Constants.MODID, id);
        return RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, tropicID);
    }

    public static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String id, F feature){
        Identifier tropicID = new Identifier(Constants.MODID, id);
        if (Registry.FEATURE.getIds().contains(tropicID))
            throw new IllegalStateException("Feature ID: \"" + tropicID.toString() + "\" already exists in the Features registry!");

        Registry.register(Registry.FEATURE, tropicID, feature);
        TropicraftFeatures.features.add(feature);
        return feature;
    }

    private static <FC extends FeatureConfig, S extends StructureFeature<FC>> S registerFeatureStructure(String id, S structureFeature, GenerationStep.Feature step) {
        Identifier tropicID = new Identifier(Constants.MODID, id);
        if (Registry.STRUCTURE_FEATURE.getIds().contains(tropicID))
            throw new IllegalStateException("Structure Feature ID: \"" + tropicID.toString() + "\" already exists in the Structure Features registry!");

        //Registry.register(Registry.STRUCTURE_PIECE, tropicID, step);
        return FabricStructureBuilder.create(tropicID, structureFeature)
                .step(step)
                .defaultConfig(32, 8, 12345)
                .adjustsSurface()
                .register();


        //TropicraftFeatures.structureFeatures.add(structureFeature);
        //return StructureFeatureInvoker.invokeRegister(id, structureFeature, step);
    }


    public static <FC extends FeatureConfig, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF registerConfiguredFeature(String id, CF configuredFeature) {
        Identifier tropicID = new Identifier(Constants.MODID, id);
        if (BuiltinRegistries.CONFIGURED_FEATURE.getIds().contains(tropicID))
            throw new IllegalStateException("Configured Feature ID: \"" + tropicID.toString() + "\" already exists in the Configured Features registry!");

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, tropicID, configuredFeature);
        return configuredFeature;
    }

    public static void init(){
        setStructurePoolsProjectionMap();
    }
}
