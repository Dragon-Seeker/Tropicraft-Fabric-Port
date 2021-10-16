package net.tropicraft.core.common.item.tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

public class TropicPickaxe extends PickaxeItem implements ITropicTool{

    private final TropicTiers material;

    public TropicPickaxe(TropicTiers material, int attackDamage, float attackSpeed, Item.Properties settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicTiers getItemMaterial() {
        return this.material;
    }

}
