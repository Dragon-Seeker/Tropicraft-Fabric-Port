package com.dragonseeker.tropicfabricport.common.item;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class TropicBlockItem extends BlockItem {
    public TropicBlockItem(Block block) {
        super(block, new FabricItemSettings().group(Tropicfabricport.ITEM_GROUP_BLOCKS));
    }
}
