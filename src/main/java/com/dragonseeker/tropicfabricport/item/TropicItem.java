package com.dragonseeker.tropicfabricport.item;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class TropicItem extends Item {
    public TropicItem() {
        super(new FabricItemSettings().group(Tropicfabricport.ITEM_GROUP_ITEMS));
    }
}
