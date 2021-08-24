package net.tropicraft;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.tropicraft.core.client.data.TropicraftTags;
import net.tropicraft.core.common.TropicraftCommands;
import net.tropicraft.core.common.dimension.TropicraftDimension;
import net.tropicraft.core.common.dimension.biome.TropicraftBiomeProvider;
import net.tropicraft.core.common.dimension.biome.TropicraftBiomes;
import net.tropicraft.core.common.dimension.carver.TropicraftCarvers;
import net.tropicraft.core.common.dimension.carver.TropicraftConfiguredCarvers;
import net.tropicraft.core.common.dimension.chunk.TropicraftChunkGenerator;
import net.tropicraft.core.common.dimension.feature.TropicraftConfiguredFeatures;
import net.tropicraft.core.common.dimension.feature.TropicraftConfiguredStructures;
import net.tropicraft.core.common.dimension.feature.TropicraftFeatures;
import net.tropicraft.core.common.dimension.feature.block_state_provider.TropicraftBlockStateProviders;
import net.tropicraft.core.common.dimension.feature.jigsaw.TropicraftProcessorLists;
import net.tropicraft.core.common.dimension.feature.pools.TropicraftTemplatePools;
import net.tropicraft.core.common.dimension.surfacebuilders.TropicraftConfiguredSurfaceBuilders;
import net.tropicraft.core.common.dimension.surfacebuilders.TropicraftSurfaceBuilders;
import net.tropicraft.core.common.registry.*;
import net.tropicraft.core.common.sound.Sounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    /*
    public static ItemGroup ITEM_GROUP_FOOD = FabricItemGroupBuilder.build(
            new Identifier(Constants.MODID, "food"),
            () -> new ItemStack(TropicraftItems.ORANGE)); //RED_ANTHURIUM

     */

    /*
    public static ItemGroup ITEM_GROUP_ARMOR_TOOLS = FabricItemGroupBuilder.build(
            new Identifier(Constants.MODID, "armorAndTools"),
            () -> new ItemStack(TropicraftItems.ORANGE)); //RED_ANTHURIUM

     */



    @Override
    public void onInitialize() {
        Sounds.init();

        TropicraftItems.init();
        TropicraftBlocks.init();

        TropicraftTags.Blocks.init();
        TropicraftTags.Items.init();

        TropicraftEntityAttributes.init();

        TropicraftEntities.init();
        TropicraftEntities.registerSpawns();
        TropicraftEntities.registerEntityAttributes();



        TropicBlockEntities.init();
        TropicScreenHandler.init();

        TropicraftDimension.addDefaultDimensionKey();
        registerWorldGen();

        TropicraftCommands.commandInitalization();
    }

    public static void registerWorldGen(){
        TropicraftBlockStateProviders.init();

        TropicraftProcessorLists.processorListsRegister();

        TropicraftFeatures.init();

        TropicraftConfiguredFeatures.configuredFeatureInit();

        TropicraftTemplatePools.structurePoolRegistery();

        TropicraftConfiguredStructures.registerConfiguredStructures();

        TropicraftChunkGenerator.register();

        TropicraftCarvers.init();
        TropicraftConfiguredCarvers.configuredCarversRegister();

        TropicraftSurfaceBuilders.init();
        TropicraftConfiguredSurfaceBuilders.ConfiguredSurfaceBuildersRegister();

        TropicraftBiomes.regisisterBiomes();
        TropicraftBiomes.vanillaBiomoeModificationInit();

        TropicraftBiomeProvider.register();
    }

}
