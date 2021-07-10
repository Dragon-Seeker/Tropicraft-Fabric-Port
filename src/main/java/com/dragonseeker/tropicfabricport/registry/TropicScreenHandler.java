package com.dragonseeker.tropicfabricport.registry;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import com.dragonseeker.tropicfabricport.block.testContainer.BoxChestScreenHandler;
import com.dragonseeker.tropicfabricport.block.testContainer.BoxScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class TropicScreenHandler  {
    public static final ScreenHandlerType<BoxScreenHandler> BOX_SCREEN_HANDLER = null;
    public static final ScreenHandlerType<BoxChestScreenHandler> BOX_CHEST_SCREEN_HANDLER;

    static {
        //BOX_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(Tropicfabricport.MOD_ID, "box_block"), BoxScreenHandler::new);
        BOX_CHEST_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(Tropicfabricport.MOD_ID, "box_chest_block"), BoxChestScreenHandler::new);
    }


    public static void init(){

    }
}
