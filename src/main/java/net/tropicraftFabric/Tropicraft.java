package net.tropicraftFabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.tropicraftFabric.client.data.TropicraftTags;
import net.tropicraftFabric.common.dimension.TropicraftDimension;
import net.tropicraftFabric.common.dimension.biome.TropicraftBiomeProvider;
import net.tropicraftFabric.common.dimension.carver.TropicraftCarvers;
import net.tropicraftFabric.common.dimension.chunk.TropicraftChunkGenerator;
import net.tropicraftFabric.common.dimension.feature.TropicraftFeatures;
import net.tropicraftFabric.common.dimension.surfacebuilders.TropicraftSurfaceBuilders;
import net.tropicraftFabric.common.registry.*;
import net.tropicraftFabric.common.sound.Sounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.entity.EntityType.COW;

public class Tropicraft implements ModInitializer {

    public static final Logger LOG = LogManager.getLogger(Constants.MODID);

    public static Identifier locate(String location) {
        return new Identifier(Constants.MODID, location);
    }

    public static final ItemGroup ITEM_GROUP_BLOCKS = FabricItemGroupBuilder.build(
            new Identifier(Constants.MODID, "blocks"),
            () -> new ItemStack(TropicraftBlocks.CHUNK)
            );

    public static ItemGroup ITEM_GROUP_ITEMS = FabricItemGroupBuilder.build(
            new Identifier(Constants.MODID, "items"),
            () -> new ItemStack(TropicraftBlocks.RED_ANTHURIUM)); //RED_ANTHURIUM


    @Override
    public void onInitialize() {
        Sounds.init();

        TropicraftItems.init();
        TropicraftBlocks.init();

        TropicraftTags.Blocks.init();
        TropicraftTags.Items.init();

        TropicraftEntities.init();
        TropicraftEntities.registerSpawns();
        TropicraftEntities.registerEntityAttributes();

        TropicBlockEntities.init();
        TropicScreenHandler.init();


        TropicraftDimension.addDefaultDimensionKey();

        TropicraftChunkGenerator.register();
        TropicraftBiomeProvider.register();

        TropicraftCarvers.init();
        TropicraftFeatures.init();

        TropicraftSurfaceBuilders.init();

        /*
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ServerWorld overworld = server.getWorld(World.OVERWORLD);
            ServerWorld world = server.getWorld(WORLD_KEY);

            if (world == null) throw new AssertionError("Test world doesn't exist.");

            Entity entity = COW.create(overworld);

            if (!entity.world.getRegistryKey().equals(World.OVERWORLD)) throw new AssertionError("Entity starting world isn't the overworld");

            TeleportTarget target = new TeleportTarget(Vec3d.ZERO, new Vec3d(1, 1, 1), 45f, 60f);

            Entity teleported = FabricDimensions.teleport(entity, world, target);

            if (teleported == null) throw new AssertionError("Entity didn't teleport");

            if (!teleported.world.getRegistryKey().equals(WORLD_KEY)) throw new AssertionError("Target world not reached.");

            if (!teleported.getPos().equals(target.position)) throw new AssertionError("Target Position not reached.");
        });

         */



    }

}
