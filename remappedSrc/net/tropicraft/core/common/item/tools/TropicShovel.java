package net.tropicraft.core.common.item.tools;

import net.minecraft.world.item.ShovelItem;

public class TropicShovel extends ShovelItem implements ITropicTool{

    private final TropicTiers material;

    public TropicShovel(TropicTiers material, float attackDamage, float attackSpeed, Properties settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public TropicTiers getItemMaterial() {
        return this.material;
    }
}
