package com.dragonseeker.tropicfabricport.common.item.tools;

import com.dragonseeker.tropicfabricport.common.registry.TropicItems;
import net.minecraft.item.HoeItem;

public class TropicHoe extends HoeItem implements ITropicTool{

    private final TropicItems.TropicTiers material;

    public TropicHoe(TropicItems.TropicTiers material, int attackDamage, float attackSpeed, Settings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicItems.TropicTiers getItemMaterial() {
        return this.material;
    }
}
