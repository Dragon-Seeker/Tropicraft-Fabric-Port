package com.dragonseeker.tropicfabricport;

import com.dragonseeker.tropicfabricport.block.TropicFlowers;
import com.dragonseeker.tropicfabricport.common.TropicraftTags;
import com.dragonseeker.tropicfabricport.registry.*;
import com.dragonseeker.tropicfabricport.sound.Sounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Tropicfabricport implements ModInitializer {

    public static final String MOD_ID = "tropicraft";
    public static final Logger LOG = LogManager.getLogger(MOD_ID);

    public static Identifier locate(String location) {
        return new Identifier(MOD_ID, location);
    }

    public static final ItemGroup ITEM_GROUP_BLOCKS = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "blocks"),
            () -> new ItemStack(TropicBlocks.CHUNK)
            );

    public static ItemGroup ITEM_GROUP_ITEMS = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "items"),
            () -> new ItemStack(TropicBlocks.RED_ANTHURIUM)); //RED_ANTHURIUM


    @Override
    public void onInitialize() {
        Sounds.init();

        TropicItems.init();
        TropicBlocks.init();

        TropicraftTags.Blocks.init();
        TropicraftTags.Items.init();

        //FabricDataGeneratorTagBuilder



        TropicEntities.init();
        TropicEntityAttributes.init();

        TropicBlockEntities.init();
        TropicScreenHandler.init();


        //modBus.addListener(this::gatherData);
        //FabricDataGenerator

    }
    /*
    private void gatherData(GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            TropicraftBlockstateProvider blockstates = new TropicraftBlockstateProvider(gen, existingFileHelper);
            gen.install(blockstates);
            gen.install(new TropicraftItemModelProvider(gen, blockstates.getExistingHelper()));
            gen.install(new TropicraftLangProvider(gen));
        }
        if (event.includeServer()) {
            TropicraftBlockTagsProvider blockTags = new TropicraftBlockTagsProvider(gen, existingFileHelper);
            gen.install(blockTags);
            gen.install(new TropicraftItemTagsProvider(gen, blockTags, existingFileHelper));
            gen.install(new TropicraftRecipeProvider(gen));
            gen.install(new TropicraftLootTableProvider(gen));
            gen.install(new TropicraftEntityTypeTagsProvider(gen, existingFileHelper));

            gatherWorldgenData(gen);
        }

    }
    */
}
