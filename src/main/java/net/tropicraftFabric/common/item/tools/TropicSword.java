package net.tropicraftFabric.common.item.tools;

import net.tropicraftFabric.common.registry.TropicraftItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.SwordItem;

public class TropicSword extends SwordItem {
    private final TropicTiers material;

    public TropicSword(TropicTiers material, int attackDamage, float attackSpeed, FabricItemSettings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }


}
