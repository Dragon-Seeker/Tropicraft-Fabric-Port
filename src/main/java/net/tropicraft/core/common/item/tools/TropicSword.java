package net.tropicraft.core.common.item.tools;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.item.SwordItem;

public class TropicSword extends SwordItem {
    private final TropicTiers material;

    public TropicSword(TropicTiers material, int attackDamage, float attackSpeed, Properties settings) {
        super(material.getDefaultTier(), attackDamage, attackSpeed, settings);
        this.material = material;
    }


}
