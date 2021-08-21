package net.tropicraft.core.common.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class CoffeeBeanItem extends BlockItem {
    public CoffeeBeanItem(Block block, FabricItemSettings settings) {
        super(block, settings);
    }

    @Override
    public String getTranslationKey() {
        return this.getOrCreateTranslationKey();
    }
}
