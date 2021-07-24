package com.dragonseeker.tropicfabricport.common.item.tools;

import com.dragonseeker.tropicfabricport.common.registry.TropicItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.SwordItem;

public class TropicSword extends SwordItem {
    private final TropicItems.TropicTiers material;

    public TropicSword(TropicItems.TropicTiers material, int attackDamage, float attackSpeed, FabricItemSettings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }


}
