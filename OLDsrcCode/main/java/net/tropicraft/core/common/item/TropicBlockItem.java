package net.tropicraft.core.common.item;

import net.tropicraft.Tropicraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class TropicBlockItem extends BlockItem {
    public TropicBlockItem(Block block) {
        super(block, new FabricItemSettings().group(Tropicraft.ITEM_GROUP_BLOCKS));
    }
}
