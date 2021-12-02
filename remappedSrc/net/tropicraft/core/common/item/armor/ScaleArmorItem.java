package net.tropicraft.core.common.item.armor;

import net.tropicraft.core.common.item.ArmorMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.entity.EquipmentSlot;


public class ScaleArmorItem extends TropicraftArmorItem {
    public ScaleArmorItem(EquipmentSlot slotType, FabricItemSettings properties) {
        super(ArmorMaterials.SCALE_ARMOR, slotType, properties);
    }

    // TODO waiting on forge???
//    @Override
//    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
//        if (source == DamageSource.IN_FIRE || source == DamageSource.LAVA) {
//            // Invincible to fire damage
//            return new ArmorProperties(10, 1.0, Integer.MAX_VALUE);
//        } else {
//            return super.getProperties(player, armor, source, damage, slot);
//        }
//    }
}
