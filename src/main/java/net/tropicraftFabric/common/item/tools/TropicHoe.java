package net.tropicraftFabric.common.item.tools;

import net.tropicraftFabric.common.registry.TropicraftItems;
import net.minecraft.item.HoeItem;

public class TropicHoe extends HoeItem implements ITropicTool{

    private final TropicraftItems.TropicTiers material;

    public TropicHoe(TropicraftItems.TropicTiers material, int attackDamage, float attackSpeed, Settings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicraftItems.TropicTiers getItemMaterial() {
        return this.material;
    }
}
