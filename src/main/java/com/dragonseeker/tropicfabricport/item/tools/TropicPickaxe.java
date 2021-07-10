package com.dragonseeker.tropicfabricport.item.tools;

import com.dragonseeker.tropicfabricport.item.TropicItemTier;
import com.dragonseeker.tropicfabricport.registry.TropicItems;
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
