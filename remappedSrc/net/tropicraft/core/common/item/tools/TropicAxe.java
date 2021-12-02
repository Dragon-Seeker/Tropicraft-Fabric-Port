package net.tropicraft.core.common.item.tools;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

public class TropicAxe extends AxeItem implements ITropicTool{

    private final TropicTiers material;

    public TropicAxe(TropicTiers material, float attackDamage, float attackSpeed, Item.Properties settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicTiers getItemMaterial() {
        return this.material;
    }
}
