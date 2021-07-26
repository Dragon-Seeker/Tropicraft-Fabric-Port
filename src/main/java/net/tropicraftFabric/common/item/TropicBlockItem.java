package net.tropicraftFabric.common.item;

import net.tropicraftFabric.Tropicraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class TropicBlockItem extends BlockItem {
    public TropicBlockItem(Block block) {
        super(block, new FabricItemSettings().group(Tropicraft.ITEM_GROUP_BLOCKS));
    }
}
