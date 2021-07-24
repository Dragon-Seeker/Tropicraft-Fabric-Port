package com.dragonseeker.tropicfabricport.common.item.tools;

import com.dragonseeker.tropicfabricport.common.registry.TropicItems;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;

public class TropicAxe extends AxeItem implements ITropicTool{

    private final TropicItems.TropicTiers material;

    public TropicAxe(TropicItems.TropicTiers material, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicItems.TropicTiers getItemMaterial() {
        return this.material;
    }
}
