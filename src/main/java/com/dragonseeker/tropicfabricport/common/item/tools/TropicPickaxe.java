package com.dragonseeker.tropicfabricport.common.item.tools;

import com.dragonseeker.tropicfabricport.common.registry.TropicItems;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class TropicPickaxe extends PickaxeItem implements ITropicTool{

    private final TropicItems.TropicTiers material;

    public TropicPickaxe(TropicItems.TropicTiers material, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicItems.TropicTiers getItemMaterial() {
        return this.material;
    }

}
