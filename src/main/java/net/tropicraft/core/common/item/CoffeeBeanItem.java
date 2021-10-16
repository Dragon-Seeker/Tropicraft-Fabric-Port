package net.tropicraft.core.common.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class CoffeeBeanItem extends BlockItem {
    public CoffeeBeanItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
