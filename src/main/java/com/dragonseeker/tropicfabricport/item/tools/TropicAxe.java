package com.dragonseeker.tropicfabricport.item.tools;

import com.dragonseeker.tropicfabricport.registry.TropicItems;
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
