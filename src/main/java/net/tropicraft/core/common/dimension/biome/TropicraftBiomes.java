package net.tropicraft.core.common.dimension.biome;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
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

    public static final ResourceKey<Biome> TROPICS_OCEAN = key("tropics_ocean");
    public static final ResourceKey<Biome> TROPICS = key("tropics");
    public static final ResourceKey<Biome> KELP_FOREST = key("kelp_forest");
    public static final ResourceKey<Biome> RAINFOREST_PLAINS = key("rainforest_plains");
    public static final ResourceKey<Biome> RAINFOREST_HILLS = key("rainforest_hills");
    public static final ResourceKey<Biome> RAINFOREST_MOUNTAINS = key("rainforest_mountains");
    public static final ResourceKey<Biome> RAINFOREST_ISLAND_MOUNTAINS = key("rainforest_island_mountains");
    public static final ResourceKey<Biome> TROPICS_RIVER = key("tropics_river");
    public static final ResourceKey<Biome> TROPICS_BEACH = key("tropics_beach");

    private static ResourceKey<Biome> key(String id) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Constants.MODID, id));
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

        tropics = Registry.register(BuiltinRegistries.BIOME, TROPICS.location(), createTropics());

        //this.tropics = Registry.register(TROPICS, createTropics());


        tropicsBeach = Registry.register(BuiltinRegistries.BIOME, TROPICS_BEACH.location(), createTropicsBeach());
        rainforestPlains = Registry.register(BuiltinRegistries.BIOME, RAINFOREST_PLAINS.location(), createRainforest(0.25F, 0.1F));
        rainforestHills = Registry.register(BuiltinRegistries.BIOME, RAINFOREST_HILLS.location(), createRainforest(0.45F, 0.425F));
        rainforestMountains = Registry.register(BuiltinRegistries.BIOME, RAINFOREST_MOUNTAINS.location(), createRainforest(0.8F, 0.8F));
        rainforestIslandMountains = Registry.register(BuiltinRegistries.BIOME, RAINFOREST_ISLAND_MOUNTAINS.location(), createRainforest(0.1F, 1.2F));

        tropicsOcean = Registry.register(BuiltinRegistries.BIOME, TROPICS_OCEAN.location(), createTropicsOcean());
        kelpForest = Registry.register(BuiltinRegistries.BIOME, KELP_FOREST.location(), createKelpForest());

        tropicsRiver = Registry.register(BuiltinRegistries.BIOME, TROPICS_RIVER.location(), createTropicsRiver());

    }

    public static void vanillaBiomoeModificationInit(){
        Predicate<BiomeSelectionContext> biomeSelectorBeach = BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.BiomeCategory.BEACH));
        Predicate<BiomeSelectionContext> biomeSelectorJungle = BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.BiomeCategory.JUNGLE));

        BiomeModifications.addFeature(biomeSelectorBeach,
                GenerationStep.Decoration.VEGETAL_DECORATION,
                TropicraftFeatures.NORMAL_PALM_TREE_CONFIGURED_KEY);

        BiomeModifications.addFeature(biomeSelectorBeach,
                GenerationStep.Decoration.VEGETAL_DECORATION,
                TropicraftFeatures.CURVED_PALM_TREE_CONFIGURED_KEY);

        BiomeModifications.addFeature(biomeSelectorBeach,
                GenerationStep.Decoration.VEGETAL_DECORATION,
                TropicraftFeatures.LARGE_PALM_TREE_CONFIGURED_KEY);

        BiomeModifications.addFeature(biomeSelectorJungle,
                GenerationStep.Decoration.VEGETAL_DECORATION,
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
        BiomeGenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(TropicraftConfiguredSurfaceBuilders.tropics);

        TropicraftConfiguredCarvers.addLand(generation);

        TropicraftConfiguredFeatures.addFruitTrees(generation);
        TropicraftConfiguredFeatures.addPalmTrees(generation);
        TropicraftConfiguredFeatures.addEih(generation);
        TropicraftConfiguredFeatures.addTropicsFlowers(generation);
        TropicraftConfiguredFeatures.addPineapples(generation);

        BiomeDefaultFeatures.addDefaultGrass(generation);
        BiomeDefaultFeatures.addSavannaGrass(generation);

        MobSpawnSettings.Builder spawns = defaultSpawns();
        spawns.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(TropicraftEntities.FAILGULL, 10, 5, 15));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TropicraftEntities.TROPI_BEE, 10, 4, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TropicraftEntities.COWKTAIL, 10, 4, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TropicraftEntities.TREE_FROG, 4, 4, 4));

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(0.1F).scale(0.1F)
                .temperature(2.0F).downfall(1.5F)
                .biomeCategory(Biome.BiomeCategory.PLAINS)
                .generationSettings(generation.build())
                .mobSpawnSettings(spawns.build())
                .specialEffects(defaultAmbience().build())
                .build();
    }

    private static Biome createTropicsBeach() {
        BiomeGenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(TropicraftConfiguredSurfaceBuilders.sandy);

        TropicraftConfiguredCarvers.addUnderwater(generation);

        TropicraftConfiguredFeatures.addPalmTrees(generation);
        TropicraftConfiguredFeatures.addTropicsFlowers(generation);

        generation.addStructureStart(TropicraftConfiguredStructures.koaVillage);

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(-0.1F).scale(0.1F)
                .temperature(1.5F).downfall(1.25F)
                .biomeCategory(Biome.BiomeCategory.BEACH)
                .generationSettings(generation.build())
                .mobSpawnSettings(defaultSpawns().build())
                .specialEffects(defaultAmbience().build())
                .build();
    }

    private static Biome createRainforest(float depth, float scale) {
        BiomeGenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(SurfaceBuilders.GRASS);

        TropicraftConfiguredCarvers.addLand(generation);

        TropicraftConfiguredFeatures.addTropicsGems(generation);
        TropicraftConfiguredFeatures.addRainforestTrees(generation);

        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.rainforestFlowers);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.coffeeBush);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TropicraftConfiguredFeatures.undergrowth);

        generation.addStructureStart(TropicraftConfiguredStructures.homeTree);

        BiomeDefaultFeatures.addJungleGrass(generation);
        BiomeDefaultFeatures.addLightBambooVegetation(generation);

        TropicraftConfiguredFeatures.addRainforestPlants(generation);

        MobSpawnSettings.Builder spawns = defaultSpawns();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 10, 1, 1));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 10, 1, 2));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.TREE_FROG, 25, 2, 5));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.TROPI_SPIDER, 30, 1, 1));

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(depth).scale(scale)
                .temperature(1.5F).downfall(2.0F)
                .biomeCategory(Biome.BiomeCategory.JUNGLE)
                .generationSettings(generation.build())
                .mobSpawnSettings(spawns.build())
                .specialEffects(defaultAmbience().build())
                .build();
    }

    private static Biome createTropicsOcean() {
        BiomeGenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(TropicraftConfiguredSurfaceBuilders.sandy);

        TropicraftConfiguredCarvers.addUnderwater(generation);

        TropicraftConfiguredFeatures.addTropicsMetals(generation);

        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.WARM_OCEAN_VEGETATION);

        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_WARM);
        TropicraftConfiguredFeatures.addUndergroundSeagrass(generation);

        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEA_PICKLE);
        TropicraftConfiguredFeatures.addUndergroundPickles(generation);

        MobSpawnSettings.Builder spawns = defaultSpawns();
        addOceanWaterCreatures(spawns);
        spawns.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(TropicraftEntities.FAILGULL, 15, 5, 10));

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(-1.6F).scale(0.4F)
                .temperature(1.5F).downfall(1.25F)
                .biomeCategory(Biome.BiomeCategory.OCEAN)
                .generationSettings(generation.build())
                .mobSpawnSettings(spawns.build())
                .specialEffects(defaultAmbience().build())
                .build();
    }

    private static Biome createKelpForest() {
        BiomeGenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(TropicraftConfiguredSurfaceBuilders.sandy);

        TropicraftConfiguredCarvers.addUnderwater(generation);

        // KELP!
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.KELP_COLD);

        TropicraftConfiguredFeatures.addUndergroundSeagrass(generation);
        TropicraftConfiguredFeatures.addUndergroundPickles(generation);

        MobSpawnSettings.Builder spawns = defaultSpawns();
        addOceanWaterCreatures(spawns);

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(-1.5F).scale(0.3F)
                .temperature(1.5F).downfall(1.25F)
                .biomeCategory(Biome.BiomeCategory.OCEAN)
                .generationSettings(generation.build())
                .mobSpawnSettings(spawns.build())
                .specialEffects(defaultAmbience().build())
                .build();
    }

    private static void addOceanWaterCreatures(MobSpawnSettings.Builder spawns) {
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.MARLIN, 10, 1, 4));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.MAN_O_WAR, 2, 1, 1));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.STARFISH, 4, 1, 4));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.SEA_URCHIN, 4, 1, 4));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.DOLPHIN, 3, 4, 7));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.SEAHORSE, 6, 6, 12));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.SEA_TURTLE, 6, 3, 8));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.TROPICAL_FISH, 20, 4, 8));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.EAGLE_RAY, 6, 1, 1));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.HAMMERHEAD, 2, 1, 1));
    }

    private static Biome createTropicsRiver() {
        BiomeGenerationSettings.Builder generation = defaultGeneration()
                .surfaceBuilder(TropicraftConfiguredSurfaceBuilders.sandy);

        TropicraftConfiguredCarvers.addLand(generation);

        TropicraftConfiguredFeatures.addTropicsFlowers(generation);

        MobSpawnSettings.Builder spawns = defaultSpawns();
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.PIRANHA, 20, 1, 12));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(TropicraftEntities.RIVER_SARDINE, 20, 1, 8));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 8, 1, 4));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COD, 4, 1, 5));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 4, 1, 5));

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .depth(-0.7F).scale(0.05F)
                .temperature(1.5F).downfall(1.25F)
                .biomeCategory(Biome.BiomeCategory.RIVER)
                .generationSettings(generation.build())
                .mobSpawnSettings(spawns.build())
                .specialEffects(defaultAmbience().build())
                .build();
    }

    private static BiomeGenerationSettings.Builder defaultGeneration() {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();

        BiomeDefaultFeatures.addDefaultOverworldLandStructures(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);

        generation.addStructureStart(TropicraftConfiguredStructures.homeTree);
        generation.addStructureStart(TropicraftConfiguredStructures.koaVillage);

        return generation;
    }

    private static MobSpawnSettings.Builder defaultSpawns() {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();

        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 20, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TropicraftEntities.V_MONKEY, 20, 1, 3));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TropicraftEntities.IGUANA, 15, 4, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TropicraftEntities.TROPI_CREEPER, 4, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TropicraftEntities.EIH, 5, 1, 1));

        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TropicraftEntities.TROPI_SKELLY, 8, 2, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TropicraftEntities.TROPI_SPIDER, 8, 2, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TropicraftEntities.ASHEN, 6, 2, 4));

        return spawns;
    }

    private static BiomeSpecialEffects.Builder defaultAmbience() {
        return new BiomeSpecialEffects.Builder()
                .fogColor(TROPICS_FOG_COLOR)
                .skyColor(TROPICS_SKY_COLOR)
                .waterColor(TROPICS_WATER_COLOR)
                .waterFogColor(TROPICS_WATER_FOG_COLOR);
    }

    private static int getSkyColor(float temperature) {
        float shift = Mth.clamp(temperature / 3.0F, -1.0F, 1.0F);
        return Mth.hsvToRgb((224.0F / 360.0F) - shift * 0.05F, 0.5F + shift * 0.1F, 1.0F);
    }
}
