package net.tropicraft.core.common.item;

import net.tropicraft.Tropicraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class TropicBlockItem extends BlockItem {
    public TropicBlockItem(Block block) {
        super(block, new FabricItemSettings().tab(Tropicraft.ITEM_GROUP_BLOCKS));
    }
}
