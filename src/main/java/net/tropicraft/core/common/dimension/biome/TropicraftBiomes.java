package net.tropicraft.core.common.dimension.biome;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import net.tropicraft.Constants;
import net.tropicraft.core.common.dimension.carver.TropicraftConfiguredCarvers;
import net.tropicraft.core.common.dimension.feature.TropicraftConfiguredFeatures;
import net.tropicraft.core.common.dimension.feature.TropicraftConfiguredStructures;
import net.tropicraft.core.common.dimension.feature.TropicraftFeatures;
import net.tropicraft.core.common.dimension.surfacebuilders.TropicraftConfiguredSurfaceBuilders;
import net.tropicraft.core.common.registry.TropicraftEntities;

import java.util.function.Predicate;

//@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class TropicraftBiomes {
    public static final int TROPICS_WATER_COLOR = 0x4eecdf;
    public static final int TROPICS_WATER_FOG_COLOR = 0x041f33;
    public static final int TROPICS_FOG_COLOR = 0xC0D8FF;
    public static final int TROPICS_SKY_COLOR = getSkyColor(0.8F);

    public static final RegistryKey<Biome> TROPICS_OCEAN = key("tropics_ocean");
    public static final RegistryKey<Biome> TROPICS = key("tropics");
    public static final RegistryKey<Biome> KELP_FOREST = key("kelp_forest");
    public static final RegistryKey<Biome> RAINFOREST_PLAINS = key("rainforest_plains");
    public static final RegistryKey<Biome> RAINFOREST_HILLS = key("rainforest_hills");
    public static final RegistryKey<Biome> RAINFOREST_MOUNTAINS = key("rainforest_mountains");
    public static final RegistryKey<Biome> RAINFOREST_ISLAND_MOUNTAINS = key("rainforest_island_mountains");
    public static final RegistryKey<Biome> TROPICS_RIVER = key("tropics_river");
    public static final RegistryKey<Biome> TROPICS_BEACH = key("tropics_beach");

    private static RegistryKey<Biome> key(String id) {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(Constants.MODID, id));
    }

    public static Biome tropics;
    public static Biome tropicsBeach;
    public static Biome rainforestPlains;
    public static Biome rainforestHills;
    public static Biome rainforestMountains;
    public static Biome rainforestIslandMountains;

    public static Biome tropicsOcean;
    public static Biome kelpForest;

    public static Biome tropicsRiver;

    //private final TropicraftConfiguredFeatures features;
    //private final TropicraftConfiguredStructures structures;
    //private final TropicraftConfiguredCarvers carvers;
    //private final TropicraftConfiguredSurfaceBuilders surfaces;

    /*
    public TropicraftBiomes(WorldgenDataConsumer<Biome> worldgen, TropicraftConfiguredFeatures features, TropicraftConfiguredStructures structures, TropicraftConfiguredCarvers carvers, TropicraftConfiguredSurfaceBuilders surfaces) {
        //this.features = features;
        this.structures = structures;
        this.carvers = carvers;
        this.surfaces = surfaces;

        this.tropics = worldgen.register(TROPICS, createTropics());

        //this.tropics = Registry.register(TROPICS, createTropics());


        this.tropicsBeach = worldgen.register(TROPICS_BEACH, createTropicsBeach());
        this.rainforestPlains = worldgen.register(RAINFOREST_PLAINS, createRainforest(0.25F, 0.1F));
        this.rainforestHills = worldgen.register(RAINFOREST_HILLS, createRainforest(0.45F, 0.425F));
        this.rainforestMountains = worldgen.register(RAINFOREST_MOUNTAINS, createRainforest(0.8F, 0.8F));
        this.rainforestIslandMountains = worldgen.register(RAINFOREST_ISLAND_MOUNTAINS, createRainforest(0.1F, 1.2F));

        this.tropicsOcean = worldgen.register(TROPICS_OCEAN, createTropicsOcean());
        this.kelpForest = worldgen.register(KELP_FOREST, createKelpForest());

        this.tropicsRiver = worldgen.register(TROPICS_RIVER, createTropicsRiver());
    }
     */

    public static void regisisterBiomes(){

        tropics = Registry.register(BuiltinRegistries.BIOME, TROPICS.getValue(), createTropics());

        //this.tropics = Registry.register(TROPICS, createTropics());


        tropicsBeach = Registry.register(BuiltinRegistries.BIOME, TROPICS_BEACH.getValue(), createTropicsBeach());
        rainforestPlains = Registry.register(BuiltinRegistries.BIOME, RAINFOREST_PLAINS.getValue(), createRainforest(0.25F, 0.1F));
        rainforestHills = Registry.register(BuiltinRegistries.BIOME, RAINFOREST_HILLS.getValue(), createRainforest(0.45F, 0.425F));
        rainforestMountains = Registry.register(BuiltinRegistries.BIOME, RAINFOREST_MOUNTAINS.getValue(), createRainforest(0.8F, 0.8F));
        rainforestIslandMountains = Registry.register(BuiltinRegistries.BIOME, RAINFOREST_ISLAND_MOUNTAINS.getValue(), createRainforest(0.1F, 1.2F));

        tropicsOcean = Registry.register(BuiltinRegistries.BIOME, TROPICS_OCEAN.getValue(), createTropicsOcean());
        kelpForest = Registry.register(BuiltinRegistries.BIOME, KELP_FOREST.getValue(), createKelpForest());

        tropicsRiver = Registry.register(BuiltinRegistries.BIOME, TROPICS_RIVER.getValue(), createTropicsRiver());

    }

    public static void vanillaBiomoeModificationInit(){
        Predicate<BiomeSelectionContext> biomeSelectorBeach = BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.Category.BEACH));
        Predicate<BiomeSelectionContext> biomeSelectorJungle = BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.Category.JUNGLE));

        BiomeModifications.addFeature(biomeSelectorBeach,
                GenerationStep.Feature.VEGETAL_DECORATION,
                TropicraftFeatures.NORMAL_PALM_TREE_CONFIGURED_KEY);

        BiomeModifications.addFeature(biomeSelectorBeach,
                GenerationStep.Feature.VEGETAL_DECORATION,
                TropicraftFeatures.CURVED_PALM_TREE_CONFIGURED_KEY);

        BiomeModifications.addFeature(biomeSelectorBeach,
                GenerationStep.Feature.VEGETAL_DECORATION,
                TropicraftFeatures.LARGE_PALM_TREE_CONFIGURED_KEY);

        BiomeModifications.addFeature(biomeSelectorJungle,
                GenerationStep.Feature.VEGETAL_DECORATION,
                TropicraftFeatures.PINEAPPLE_PATCH_OVERWORLD_KEY);
    }


    /*
    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        Identifier name = event.getName();
        if (name != null && name.getNamespace().equals(Constants.MODID)) {
            return;
        }

        Biome.Category category = event.getCategory();

        Biome.Precipitation precipitation = event.getClimate().precipitation;
        if (precipitation == Biome.Precipitation.SNOW) {
            return;
        }

        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        if (category == Biome.Category.BEACH) {
            generation.feature(GenerationStep.Feature.VEGETAL_DECORATION,
                    TropicraftFeatures.NORMAL_PALM_TREE.configure(DefaultFeatureConfig.INSTANCE)
                            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.08F, 1)))
            );
            generation.feature(GenerationStep.Feature.VEGETAL_DECORATION,
                    TropicraftFeatures.CURVED_PALM_TREE.configure(DefaultFeatureConfig.INSTANCE)
                            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.08F, 1)))
            );
            generation.feature(GenerationStep.Feature.VEGETAL_DECORATION,
                    TropicraftFeatures.LARGE_PALM_TREE.configure(DefaultFeatureConfig.INSTANCE)
                            .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.08F, 1)))
            );
        } else if (category == Biome.Category.JUNGLE) {
            SimpleBlockStateProvider state = new SimpleBlockStateProvider(TropicraftBlocks.PINEAPPLE.getDefaultState());
            generation.feature(GenerationStep.Feature.VEGETAL_DECORATION,
                    Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(state, new DoublePlantPlacer())
                            .tries(6)
                            .canReplace()
                            .build()
                    ).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
            );
        }
    }
     */

    private static Biome createTropics() {
        GenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(TropicraftConfiguredSurfaceBuilders.tropics);

        TropicraftConfiguredCarvers.addLand(generation);

        TropicraftConfiguredFeatures.addFruitTrees(generation);
        TropicraftConfiguredFeatures.addPalmTrees(generation);
        TropicraftConfiguredFeatures.addEih(generation);
        TropicraftConfiguredFeatures.addTropicsFlowers(generation);
        TropicraftConfiguredFeatures.addPineapples(generation);

        DefaultBiomeFeatures.addDefaultGrass(generation);
        DefaultBiomeFeatures.addSavannaTallGrass(generation);

        SpawnSettings.Builder spawns = defaultSpawns();
        spawns.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(TropicraftEntities.FAILGULL, 10, 5, 15));
        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TropicraftEntities.TROPI_BEE, 10, 4, 4));
        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TropicraftEntities.COWKTAIL, 10, 4, 4));
        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TropicraftEntities.TREE_FROG, 4, 4, 4));

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(0.1F).scale(0.1F)
                .temperature(2.0F).downfall(1.5F)
                .category(Biome.Category.PLAINS)
                .generationSettings(generation.build())
                .spawnSettings(spawns.build())
                .effects(defaultAmbience().build())
                .build();
    }

    private static Biome createTropicsBeach() {
        GenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(TropicraftConfiguredSurfaceBuilders.sandy);

        TropicraftConfiguredCarvers.addUnderwater(generation);

        TropicraftConfiguredFeatures.addPalmTrees(generation);
        TropicraftConfiguredFeatures.addTropicsFlowers(generation);

        generation.structureFeature(TropicraftConfiguredStructures.koaVillage);

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(-0.1F).scale(0.1F)
                .temperature(1.5F).downfall(1.25F)
                .category(Biome.Category.BEACH)
                .generationSettings(generation.build())
                .spawnSettings(defaultSpawns().build())
                .effects(defaultAmbience().build())
                .build();
    }

    private static Biome createRainforest(float depth, float scale) {
        GenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

        TropicraftConfiguredCarvers.addLand(generation);

        TropicraftConfiguredFeatures.addTropicsGems(generation);
        TropicraftConfiguredFeatures.addRainforestTrees(generation);

        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestFlowers);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.coffeeBush);
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, TropicraftConfiguredFeatures.undergrowth);

        generation.structureFeature(TropicraftConfiguredStructures.homeTree);

        DefaultBiomeFeatures.addJungleGrass(generation);
        DefaultBiomeFeatures.addBamboo(generation);

        TropicraftConfiguredFeatures.addRainforestPlants(generation);

        SpawnSettings.Builder spawns = defaultSpawns();
        spawns.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.OCELOT, 10, 1, 1));
        spawns.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PARROT, 10, 1, 2));
        spawns.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.TREE_FROG, 25, 2, 5));
        spawns.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.TROPI_SPIDER, 30, 1, 1));

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(depth).scale(scale)
                .temperature(1.5F).downfall(2.0F)
                .category(Biome.Category.JUNGLE)
                .generationSettings(generation.build())
                .spawnSettings(spawns.build())
                .effects(defaultAmbience().build())
                .build();
    }

    private static Biome createTropicsOcean() {
        GenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(TropicraftConfiguredSurfaceBuilders.sandy);

        TropicraftConfiguredCarvers.addUnderwater(generation);

        TropicraftConfiguredFeatures.addTropicsMetals(generation);

        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.WARM_OCEAN_VEGETATION);

        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEAGRASS_WARM);
        TropicraftConfiguredFeatures.addUndergroundSeagrass(generation);

        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SEA_PICKLE);
        TropicraftConfiguredFeatures.addUndergroundPickles(generation);

        SpawnSettings.Builder spawns = defaultSpawns();
        addOceanWaterCreatures(spawns);
        spawns.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(TropicraftEntities.FAILGULL, 15, 5, 10));

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(-1.6F).scale(0.4F)
                .temperature(1.5F).downfall(1.25F)
                .category(Biome.Category.OCEAN)
                .generationSettings(generation.build())
                .spawnSettings(spawns.build())
                .effects(defaultAmbience().build())
                .build();
    }

    private static Biome createKelpForest() {
        GenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(TropicraftConfiguredSurfaceBuilders.sandy);

        TropicraftConfiguredCarvers.addUnderwater(generation);

        // KELP!
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.KELP_COLD);

        TropicraftConfiguredFeatures.addUndergroundSeagrass(generation);
        TropicraftConfiguredFeatures.addUndergroundPickles(generation);

        SpawnSettings.Builder spawns = defaultSpawns();
        addOceanWaterCreatures(spawns);

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(-1.5F).scale(0.3F)
                .temperature(1.5F).downfall(1.25F)
                .category(Biome.Category.OCEAN)
                .generationSettings(generation.build())
                .spawnSettings(spawns.build())
                .effects(defaultAmbience().build())
                .build();
    }

    private static void addOceanWaterCreatures(SpawnSettings.Builder spawns) {
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.MARLIN, 10, 1, 4));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.MAN_O_WAR, 2, 1, 1));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.STARFISH, 4, 1, 4));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.SEA_URCHIN, 4, 1, 4));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.DOLPHIN, 3, 4, 7));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.SEAHORSE, 6, 6, 12));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.SEA_TURTLE, 6, 3, 8));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.TROPICAL_FISH, 20, 4, 8));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.EAGLE_RAY, 6, 1, 1));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.HAMMERHEAD, 2, 1, 1));
    }

    private static Biome createTropicsRiver() {
        GenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(TropicraftConfiguredSurfaceBuilders.sandy);

        TropicraftConfiguredCarvers.addLand(generation);

        TropicraftConfiguredFeatures.addTropicsFlowers(generation);

        SpawnSettings.Builder spawns = defaultSpawns();
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.PIRANHA, 20, 1, 12));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(TropicraftEntities.RIVER_SARDINE, 20, 1, 8));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SQUID, 8, 1, 4));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.COD, 4, 1, 5));
        spawns.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.SALMON, 4, 1, 5));

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(-0.7F).scale(0.05F)
                .temperature(1.5F).downfall(1.25F)
                .category(Biome.Category.RIVER)
                .generationSettings(generation.build())
                .spawnSettings(spawns.build())
                .effects(defaultAmbience().build())
                .build();
    }

    private static GenerationSettings.Builder defaultGeneration() {
        GenerationSettings.Builder generation = new GenerationSettings.Builder();

        DefaultBiomeFeatures.addDefaultUndergroundStructures(generation);
        DefaultBiomeFeatures.addDefaultOres(generation);
        DefaultBiomeFeatures.addMineables(generation);

        generation.structureFeature(TropicraftConfiguredStructures.homeTree);
        generation.structureFeature(TropicraftConfiguredStructures.koaVillage);

        return generation;
    }

    private static SpawnSettings.Builder defaultSpawns() {
        SpawnSettings.Builder spawns = new SpawnSettings.Builder();

        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.PARROT, 20, 1, 2));
        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TropicraftEntities.V_MONKEY, 20, 1, 3));
        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TropicraftEntities.IGUANA, 15, 4, 4));
        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TropicraftEntities.TROPI_CREEPER, 4, 1, 2));
        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TropicraftEntities.EIH, 5, 1, 1));

        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TropicraftEntities.TROPI_SKELLY, 8, 2, 4));
        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TropicraftEntities.TROPI_SPIDER, 8, 2, 2));
        spawns.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TropicraftEntities.ASHEN, 6, 2, 4));

        return spawns;
    }

    private static BiomeEffects.Builder defaultAmbience() {
        return new BiomeEffects.Builder()
                .fogColor(TROPICS_FOG_COLOR)
                .skyColor(TROPICS_SKY_COLOR)
                .waterColor(TROPICS_WATER_COLOR)
                .waterFogColor(TROPICS_WATER_FOG_COLOR);
    }

    private static int getSkyColor(float temperature) {
        float shift = MathHelper.clamp(temperature / 3.0F, -1.0F, 1.0F);
        return MathHelper.hsvToRgb((224.0F / 360.0F) - shift * 0.05F, 0.5F + shift * 0.1F, 1.0F);
    }
}
