package net.tropicraftFabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.tropicraftFabric.client.data.TropicraftTags;
import net.tropicraftFabric.common.registry.*;
import net.tropicraftFabric.common.sound.Sounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Tropicraft implements ModInitializer {

    public static final Logger LOG = LogManager.getLogger(Constants.MODID);

    public static Identifier locate(String location) {
        return new Identifier(Constants.MODID, location);
    }

    public static final ItemGroup ITEM_GROUP_BLOCKS = FabricItemGroupBuilder.build(
            new Identifier(Constants.MODID, "blocks"),
            () -> new ItemStack(TropicBlocks.CHUNK)
            );

    public static ItemGroup ITEM_GROUP_ITEMS = FabricItemGroupBuilder.build(
            new Identifier(Constants.MODID, "items"),
            () -> new ItemStack(TropicBlocks.RED_ANTHURIUM)); //RED_ANTHURIUM


    @Override
    public void onInitialize() {
        Sounds.init();

        TropicraftItems.init();
        TropicBlocks.init();

        TropicraftTags.Blocks.init();
        TropicraftTags.Items.init();

        TropicraftEntities.init();
        TropicraftEntities.registerSpawns();
        TropicraftEntities.registerEntityAttributes();

        TropicBlockEntities.init();
        TropicScreenHandler.init();



    }

}
