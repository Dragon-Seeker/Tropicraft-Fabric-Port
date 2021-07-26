package net.tropicraftFabric.common.item.tools;

import net.tropicraftFabric.common.registry.TropicraftItems;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;

public class TropicAxe extends AxeItem implements ITropicTool{

    private final TropicraftItems.TropicTiers material;

    public TropicAxe(TropicraftItems.TropicTiers material, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicraftItems.TropicTiers getItemMaterial() {
        return this.material;
    }
}
