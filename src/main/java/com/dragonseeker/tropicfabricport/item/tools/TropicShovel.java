package com.dragonseeker.tropicfabricport.item.tools;

import com.dragonseeker.tropicfabricport.registry.TropicItems;
import net.minecraft.item.ShovelItem;

public class TropicShovel extends ShovelItem implements ITropicTool{

    private final TropicItems.TropicTiers material;

    public TropicShovel(TropicItems.TropicTiers material, float attackDamage, float attackSpeed, Settings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicItems.TropicTiers getItemMaterial() {
        return this.material;
    }
}
