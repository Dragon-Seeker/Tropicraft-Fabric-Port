package net.tropicraft.core.common.dimension;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.tropicraft.Constants;
import net.tropicraft.core.common.dimension.biome.TropicraftBiomeProvider;
import net.tropicraft.core.common.dimension.chunk.TropicraftChunkGenerator;
import net.tropicraft.core.mixins.DimensionsOptionsAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

//@Mod.EventBusSubscriber(modid = Constants.MODID)
public class TropicraftDimension {
    private static final Logger LOGGER = LogManager.getLogger(TropicraftDimension.class);

    public static final Identifier ID = new Identifier(Constants.MODID, "tropics");

    public static final RegistryKey<World> WORLD = RegistryKey.of(Registry.WORLD_KEY, ID);
    public static final RegistryKey<DimensionOptions> DIMENSION = RegistryKey.of(Registry.DIMENSION_KEY, ID);
    public static final RegistryKey<DimensionType> DIMENSION_TYPE = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, ID);
    public static final RegistryKey<ChunkGeneratorSettings> DIMENSION_SETTINGS = RegistryKey.of(Registry.NOISE_SETTINGS_WORLDGEN, ID);

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

        LinkedHashSet<RegistryKey<DimensionOptions>> keys = DimensionsOptionsAccessor.getBaseDimensions();
        keys.add(DIMENSION);
        DimensionsOptionsAccessor.setBaseDimensions(keys);




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


    public static DimensionOptions createDimension(
            Registry<DimensionType> dimensionTypeRegistry,
            Registry<Biome> biomeRegistry,
            Registry<ChunkGeneratorSettings> dimensionSettingsRegistry,
            long seed
    ) {
        Supplier<DimensionType> dimensionType = () -> dimensionTypeRegistry.getOrThrow(TropicraftDimension.DIMENSION_TYPE);
        ChunkGenerator generator = TropicraftDimension.createGenerator(biomeRegistry, dimensionSettingsRegistry, seed);

        return new DimensionOptions(dimensionType, generator);
    }

    public static ChunkGenerator createGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> dimensionSettingsRegistry, long seed) {
        Supplier<ChunkGeneratorSettings> dimensionSettings = () -> {
            // fallback to overworld so that we don't crash before our datapack is loaded (horrible workaround)
            ChunkGeneratorSettings settings = dimensionSettingsRegistry.get(DIMENSION_SETTINGS);
            return settings != null ? settings : dimensionSettingsRegistry.getOrThrow(ChunkGeneratorSettings.OVERWORLD);
        };
        TropicraftBiomeProvider biomeSource = new TropicraftBiomeProvider(seed, biomeRegistry);
        return new TropicraftChunkGenerator(biomeSource, seed, dimensionSettings);
    }

    public static void teleportPlayer(ServerPlayerEntity player, RegistryKey<World> dimensionType) {
        if (player.world.getRegistryKey() == dimensionType) {
            teleportPlayerNoPortal(player, World.OVERWORLD);
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
    public static void teleportPlayerNoPortal(ServerPlayerEntity player, RegistryKey<World> destination) {
        ServerWorld world = player.server.getWorld(destination);
        if (world == null) {
            LOGGER.error("Cannot teleport player to dimension {} as it does not exist!", destination.getValue());
            return;
        }

        //if (!ForgeHooks.onTravelToDimension(player, destination)) return;

        int x = MathHelper.floor(player.getX());
        int z = MathHelper.floor(player.getZ());

        WorldChunk chunk = world.getChunk(x >> 4, z >> 4);
        int topY = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, x & 15, z & 15);
        player.teleport(world, x + 0.5, topY + 1.0, z + 0.5, player.yaw, player.pitch);


        //BasicEventHooks.firePlayerChangedDimensionEvent(player, destination, destination);
    }
}
