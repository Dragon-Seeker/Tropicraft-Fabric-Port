package net.tropicraft.core.common.item.tools;

import net.minecraft.item.HoeItem;

public class TropicHoe extends HoeItem implements ITropicTool{

    private final TropicTiers material;

    public TropicHoe(TropicTiers material, int attackDamage, float attackSpeed, Settings settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicTiers getItemMaterial() {
        return this.material;
    }
}
