package net.tropicraft.core.common.item.armor;

import net.tropicraft.Constants;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;


public class TropicraftArmorItem extends ArmorItem {
    public static final String ARMOR_LOCATION = Constants.MODID + ":textures/models/armor/";

    public TropicraftArmorItem(ArmorMaterial armorMaterial, EquipmentSlot slotType, Properties properties) {
        super(armorMaterial, slotType, properties);
    }

    protected String getTexturePath(String name) {
        return ARMOR_LOCATION + name;
    }

    /*
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return getTexturePath(String.format("%s_layer_" + (slot == EquipmentSlot.LEGS ? 2 : 1) + ".png", getMaterial().getName()));
    }

     */
}
