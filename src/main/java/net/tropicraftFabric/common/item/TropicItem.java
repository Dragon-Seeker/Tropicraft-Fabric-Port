package net.tropicraftFabric.common.item;

import net.tropicraftFabric.Tropicraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class TropicItem extends Item {
    public TropicItem() {
        super(new FabricItemSettings().group(Tropicraft.ITEM_GROUP_ITEMS));
    }
}
