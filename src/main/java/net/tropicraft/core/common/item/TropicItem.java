package net.tropicraft.core.common.item;

import net.tropicraft.Tropicraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class TropicItem extends Item {
    public TropicItem() {
        super(new FabricItemSettings().group(Tropicraft.ITEM_GROUP_ITEMS));
    }
}
