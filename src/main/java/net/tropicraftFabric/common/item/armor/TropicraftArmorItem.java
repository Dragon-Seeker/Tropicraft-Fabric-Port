package net.tropicraftFabric.common.item.armor;

import net.tropicraftFabric.Constants;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;


public class TropicraftArmorItem extends ArmorItem {
    public static final String ARMOR_LOCATION = Constants.MODID + ":textures/models/armor/";

    public TropicraftArmorItem(ArmorMaterial armorMaterial, EquipmentSlot slotType, FabricItemSettings properties) {
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
