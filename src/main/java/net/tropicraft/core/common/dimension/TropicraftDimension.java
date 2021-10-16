package net.tropicraft.core.common.dimension;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.tropicraft.Constants;
import net.tropicraft.core.common.dimension.biome.TropicraftBiomeProvider;
import net.tropicraft.core.common.dimension.chunk.TropicraftChunkGenerator;
import net.tropicraft.core.mixins.DimensionsOptionsAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.function.Supplier;

//@Mod.EventBusSubscriber(modid = Constants.MODID)
public class TropicraftDimension {
    private static final Logger LOGGER = LogManager.getLogger(TropicraftDimension.class);

    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "tropics");

    public static final ResourceKey<Level> WORLD = ResourceKey.create(Registry.DIMENSION_REGISTRY, ID);
    public static final ResourceKey<LevelStem> DIMENSION = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, ID);
    public static final ResourceKey<DimensionType> DIMENSION_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, ID);
    public static final ResourceKey<NoiseGeneratorSettings> DIMENSION_SETTINGS = ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, ID);

    //public static DimensionType DIMENSION_TYPE_TEST = Registry.register(DIMENSION)


    /*
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) event.getWorld();
            if (world.getRegistryKey() == World.OVERWORLD) {
                upgradeTropicraftDimension(world.getServer());
            }
        }
    }

    private static void upgradeTropicraftDimension(MinecraftServer server) {
        // forge put dimensions in a different place to where vanilla does with its custom dimension support
        // we need to move our old data to the correct place if it exists

        LevelStorage.Session save = server.session;

        File oldDimension = save.getDirectory(new WorldSavePath("tropicraft/tropics")).toFile();
        File newDimension = save.getWorldDirectory(WORLD);
        if (oldDimension.exists() && !newDimension.exists()) {
            try {
                FileUtils.moveDirectory(oldDimension, newDimension);
            } catch (IOException e) {
                LOGGER.error("Failed to move old tropicraft dimension to new location!", e);
            }
        }
    }
    */



    @SuppressWarnings("unchecked")
    public static void addDefaultDimensionKey() {
        //Field dimensionKeysField = ObfuscationReflectionHelper.findField(DimensionOptions.class, "field_236056_e_");

        Set<ResourceKey<LevelStem>> keys = DimensionsOptionsAccessor.getBuiltInOrder();
        keys.add(DIMENSION);
        DimensionsOptionsAccessor.setBuiltInOrder(keys);




        /*
        Field dimensionKeysField = null;

        try{
            dimensionKeysField = DimensionOptions.class.getField("BASE_DIMENSIONS");

        } catch(NoSuchFieldException e){
           LOGGER.error("Failed to find Dimension Options to add tropics", e);
        }

        try{
            LinkedHashSet<RegistryKey<DimensionOptions>> keys = (LinkedHashSet<RegistryKey<DimensionOptions>>) dimensionKeysField.get(null);
            keys.add(DIMENSION);

        } catch(IllegalAccessException e){
            LOGGER.error("Failed to add tropics as a default dimension key", e);
        }

         */




        //DimensionOptions.BASE_DIMENSIONS.add(DIMENSION);

    }


    public static LevelStem createDimension(
            Registry<DimensionType> dimensionTypeRegistry,
            Registry<Biome> biomeRegistry,
            Registry<NoiseGeneratorSettings> dimensionSettingsRegistry,
            long seed
    ) {
        Supplier<DimensionType> dimensionType = () -> dimensionTypeRegistry.getOrThrow(TropicraftDimension.DIMENSION_TYPE);
        ChunkGenerator generator = TropicraftDimension.createGenerator(biomeRegistry, dimensionSettingsRegistry, seed);

        return new LevelStem(dimensionType, generator);
    }

    public static ChunkGenerator createGenerator(Registry<Biome> biomeRegistry, Registry<NoiseGeneratorSettings> dimensionSettingsRegistry, long seed) {
        Supplier<NoiseGeneratorSettings> dimensionSettings = () -> {
            // fallback to overworld so that we don't crash before our datapack is loaded (horrible workaround)
            NoiseGeneratorSettings settings = dimensionSettingsRegistry.get(DIMENSION_SETTINGS);
            return settings != null ? settings : dimensionSettingsRegistry.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
        };
        TropicraftBiomeProvider biomeSource = new TropicraftBiomeProvider(seed, biomeRegistry);
        return new TropicraftChunkGenerator(biomeSource, seed, dimensionSettings);
    }

    public static void teleportPlayer(ServerPlayer player, ResourceKey<Level> dimensionType) {
        if (player.level.dimension() == dimensionType) {
            teleportPlayerNoPortal(player, Level.OVERWORLD);
        } else {
            teleportPlayerNoPortal(player, dimensionType);
        }
    }

    /**
     * Finds the top Y position relative to the dimension the player is teleporting to and places
     * the entity at that position. Avoids portal generation by using player.teleport() instead of
     * player.changeDimension()
     *
     * @param player The player that will be teleported
     * @param destination The target dimension to teleport to
     */
    public static void teleportPlayerNoPortal(ServerPlayer player, ResourceKey<Level> destination) {
        ServerLevel world = player.server.getLevel(destination);
        if (world == null) {
            LOGGER.error("Cannot teleport player to dimension {} as it does not exist!", destination.location());
            return;
        }

        //if (!ForgeHooks.onTravelToDimension(player, destination)) return;

        int x = Mth.floor(player.getX());
        int z = Mth.floor(player.getZ());

        LevelChunk chunk = world.getChunk(x >> 4, z >> 4);
        int topY = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, x & 15, z & 15);
        player.teleportTo(world, x + 0.5, topY + 1.0, z + 0.5, player.getYRot(), player.getXRot());


        //BasicEventHooks.firePlayerChangedDimensionEvent(player, destination, destination);
    }
}
