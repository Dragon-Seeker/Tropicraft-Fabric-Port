package net.tropicraft.core.common.item.tools;

import net.minecraft.world.item.HoeItem;

public class TropicHoe extends HoeItem implements ITropicTool{

    private final TropicTiers material;

    public TropicHoe(TropicTiers material, int attackDamage, float attackSpeed, Properties settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicTiers getItemMaterial() {
        return this.material;
    }
}
