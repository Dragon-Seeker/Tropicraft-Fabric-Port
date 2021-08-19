package net.tropicraftFabric.common.item.tools;

import net.tropicraftFabric.common.registry.TropicraftItems;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class TropicPickaxe extends PickaxeItem implements ITropicTool{

    private final TropicTiers material;

    public TropicPickaxe(TropicTiers material, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicTiers getItemMaterial() {
        return this.material;
    }

}
