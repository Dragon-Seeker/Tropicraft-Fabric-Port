package net.tropicraft.core.common.registry;

import net.tropicraft.Constants;
import net.tropicraft.core.common.block.testContainer.BoxChestScreenHandler;
import net.tropicraft.core.common.block.testContainer.BoxScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

public class TropicScreenHandler  {
    public static final MenuType<BoxScreenHandler> BOX_SCREEN_HANDLER = null;
    public static final MenuType<BoxChestScreenHandler> BOX_CHEST_SCREEN_HANDLER;

    static {
        //BOX_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(Tropicfabricport.MOD_ID, "box_block"), BoxScreenHandler::new);
        BOX_CHEST_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new ResourceLocation(Constants.MODID, "box_chest_block"), BoxChestScreenHandler::new);
    }


    public static void init(){

    }
}
