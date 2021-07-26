package net.tropicraftFabric.common.item.tools;

import net.tropicraftFabric.common.registry.TropicraftItems;
import net.minecraft.item.ShovelItem;

public class TropicShovel extends ShovelItem implements ITropicTool{

    private final TropicraftItems.TropicTiers material;

    public TropicShovel(TropicraftItems.TropicTiers material, float attackDamage, float attackSpeed, Settings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicraftItems.TropicTiers getItemMaterial() {
        return this.material;
    }
}
