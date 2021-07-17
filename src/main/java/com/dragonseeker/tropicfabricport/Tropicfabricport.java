package com.dragonseeker.tropicfabricport;

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

    public static final ItemGroup ITEM_GROUP_ITEMS = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "items"),
            () -> new ItemStack(TropicBlocks.RED_ANTHURIUM)
    );

    @Override
    public void onInitialize() {
        Sounds.init();

        TropicItems.init();
        TropicBlocks.init();

        TropicEntities.init();
        TropicEntityAttributes.init();

        TropicBlockEntities.init();
        TropicScreenHandler.init();
    }
}
