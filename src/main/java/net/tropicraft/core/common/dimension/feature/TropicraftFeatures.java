package net.tropicraft.core.common.dimension.feature;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.blockplacers.DoublePlantPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.tropicraft.Constants;
import net.tropicraft.core.common.dimension.feature.config.FruitTreeConfig;
import net.tropicraft.core.common.dimension.feature.config.HomeTreeBranchConfig;
import net.tropicraft.core.common.dimension.feature.config.RainforestVinesConfig;
import net.tropicraft.core.common.dimension.feature.jigsaw.SinkInGroundProcessor;
import net.tropicraft.core.common.dimension.feature.jigsaw.SmoothingGravityProcessor;
import net.tropicraft.core.common.dimension.feature.jigsaw.SteepPathProcessor;
import net.tropicraft.core.common.dimension.feature.jigsaw.StructureSupportsProcessor;
import net.tropicraft.core.common.dimension.feature.tree.*;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import net.tropicraft.core.mixins.ProjectionFactory;

import java.util.ArrayList;
import java.util.List;

public class TropicraftFeatures extends Features{

    public static List<Feature<?>> features = new ArrayList<>();

    public static List<StructureFeature<?>> structureFeatures = new ArrayList<>();

    //public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Constants.MODID);
    //public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Constants.MODID);

    public static final FruitTreeFeature FRUIT_TREE = registerFeature("fruit_tree", new FruitTreeFeature(FruitTreeConfig.CODEC));
    public static final PalmTreeFeature NORMAL_PALM_TREE = registerFeature("normal_palm_tree", new NormalPalmTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final PalmTreeFeature CURVED_PALM_TREE = registerFeature("curved_palm_tree", new CurvedPalmTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final PalmTreeFeature LARGE_PALM_TREE = registerFeature("large_palm_tree", new LargePalmTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RainforestTreeFeature UP_TREE = registerFeature("up_tree", new UpTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RainforestTreeFeature SMALL_TUALUNG = registerFeature("small_tualung", new TualungFeature(NoneFeatureConfiguration.CODEC, 16, 9));
    public static final RainforestTreeFeature LARGE_TUALUNG = registerFeature("large_tualung", new TualungFeature(NoneFeatureConfiguration.CODEC, 25, 11));
    public static final RainforestTreeFeature TALL_TREE = registerFeature("tall_tree", new TallRainforestTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final EIHFeature EIH = registerFeature("eih", new EIHFeature(NoneFeatureConfiguration.CODEC));
    public static final UndergrowthFeature UNDERGROWTH = registerFeature("undergrowth", new UndergrowthFeature(NoneFeatureConfiguration.CODEC));
    public static final RainforestVinesFeature VINES = registerFeature("rainforest_vines", new RainforestVinesFeature(RainforestVinesConfig.CODEC));
    public static final UndergroundSeaPickleFeature UNDERGROUND_SEA_PICKLE = registerFeature("underground_sea_pickle", new UndergroundSeaPickleFeature(NoneFeatureConfiguration.CODEC));


    public static final StructureFeature<JigsawConfiguration> KOA_VILLAGE = registerFeatureStructure("koa_village", new KoaVillageStructure(JigsawConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES);
    public static final StructureFeature<JigsawConfiguration> HOME_TREE = registerFeatureStructure("home_tree", new HomeTreeStructure(JigsawConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES);
    public static final HomeTreeBranchFeature<HomeTreeBranchConfig> HOME_TREE_BRANCH = registerFeature("home_tree_branch", new HomeTreeBranchFeature(HomeTreeBranchConfig.CODEC));
    public static final CoffeePlantFeature COFFEE_BUSH = registerFeature("coffee_bush", new CoffeePlantFeature(NoneFeatureConfiguration.CODEC));

    public static final ConfiguredFeature<?, ?> NORMAL_PALM_TREE_CONFIGURED = TropicraftConfiguredFeatures.register("normal_palm_tree_configured", TropicraftFeatures.NORMAL_PALM_TREE, f -> f.configured(NoneFeatureConfiguration.INSTANCE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.08F, 1))));
    public static final ConfiguredFeature<?, ?> CURVED_PALM_TREE_CONFIGURED = TropicraftConfiguredFeatures.register("curved_palm_tree_configured", TropicraftFeatures.CURVED_PALM_TREE, f -> f.configured(NoneFeatureConfiguration.INSTANCE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.08F, 1))));
    public static final ConfiguredFeature<?, ?> LARGE_PALM_TREE_CONFIGURED = TropicraftConfiguredFeatures.register("large_palm_tree_configured", TropicraftFeatures.LARGE_PALM_TREE, f -> f.configured(NoneFeatureConfiguration.INSTANCE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.08F, 1))));
    public static final ConfiguredFeature<?, ?> PINEAPPLE_PATCH_OVERWORLD = registerConfiguredFeature("pineapple_patch_overworld",
            Feature.RANDOM_PATCH.configured(
                    new RandomPatchConfiguration.GrassConfigurationBuilder(
                        new SimpleStateProvider(
                            TropicraftBlocks.PINEAPPLE.defaultBlockState()),
                    new DoublePlantPlacer()).tries(6).canReplace().build()).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE));

    public static final ResourceKey<ConfiguredFeature<?, ?>> NORMAL_PALM_TREE_CONFIGURED_KEY = registerConfiguredFeatureKey("normal_palm_tree_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CURVED_PALM_TREE_CONFIGURED_KEY = registerConfiguredFeatureKey("curved_palm_tree_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_PALM_TREE_CONFIGURED_KEY = registerConfiguredFeatureKey("large_palm_tree_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINEAPPLE_PATCH_OVERWORLD_KEY = registerConfiguredFeatureKey("pineapple_patch_overworld");




    /*
    public static final Projection KOA_PATH =
            Projection.create("KOA_PATH",
                              Constants.MODID + ":koa_path",
                              ImmutableList.of(new SmoothingGravityProcessor(Heightmap.Type.WORLD_SURFACE_WG, -1), new SinkInGroundProcessor(), new SteepPathProcessor(), new StructureSupportsProcessor(false, ImmutableList.of(new Identifier("bamboo_fence")))); //ImmutableList.of(TropicBlocks.BAMBOO_FENCE)
     */

    public static final Projection KOA_PATH = ProjectionFactory.newProjection("KOA_PATH",
            2,
            Constants.MODID + ":koa_path",
            ImmutableList.of(new SmoothingGravityProcessor(Heightmap.Types.WORLD_SURFACE_WG, -1),
                             new SinkInGroundProcessor(),
                             new SteepPathProcessor(),
                             new StructureSupportsProcessor(false, ImmutableList.of(new ResourceLocation("bamboo_fence")))));

    public static void setStructurePoolsProjectionMap(){
        Projection.BY_NAME.put(KOA_PATH.getName(), KOA_PATH);
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

    public static ResourceKey<ConfiguredFeature<?, ?>> registerConfiguredFeatureKey(String id){
        ResourceLocation tropicID = new ResourceLocation(Constants.MODID, id);
        return ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, tropicID);
    }

    public static <C extends FeatureConfiguration, F extends Feature<C>> F registerFeature(String id, F feature){
        ResourceLocation tropicID = new ResourceLocation(Constants.MODID, id);
        if (Registry.FEATURE.keySet().contains(tropicID))
            throw new IllegalStateException("Feature ID: \"" + tropicID.toString() + "\" already exists in the Features registry!");

        Registry.register(Registry.FEATURE, tropicID, feature);
        TropicraftFeatures.features.add(feature);
        return feature;
    }

    private static <FC extends FeatureConfiguration, S extends StructureFeature<FC>> S registerFeatureStructure(String id, S structureFeature, GenerationStep.Decoration step) {
        ResourceLocation tropicID = new ResourceLocation(Constants.MODID, id);
        if (Registry.STRUCTURE_FEATURE.keySet().contains(tropicID))
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


    public static <FC extends FeatureConfiguration, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF registerConfiguredFeature(String id, CF configuredFeature) {
        ResourceLocation tropicID = new ResourceLocation(Constants.MODID, id);
        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(tropicID))
            throw new IllegalStateException("Configured Feature ID: \"" + tropicID.toString() + "\" already exists in the Configured Features registry!");

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, tropicID, configuredFeature);
        return configuredFeature;
    }

    public static void init(){
        setStructurePoolsProjectionMap();
    }
}
